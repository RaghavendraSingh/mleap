package ml.combust.mleap.bundle.ops.feature

import ml.combust.bundle.BundleContext
import ml.combust.bundle.dsl._
import ml.combust.bundle.op.{OpModel, OpNode}
import ml.combust.mleap.core.feature.PcaModel
import ml.combust.mleap.runtime.MleapContext
import ml.combust.mleap.runtime.transformer.feature.Pca
import ml.combust.mleap.tensor.DenseTensor
import org.apache.spark.ml.linalg.DenseMatrix

/**
  * Created by hollinwilkins on 10/12/16.
  */
class PcaOp extends OpNode[MleapContext, Pca, PcaModel] {
  override val Model: OpModel[MleapContext, PcaModel] = new OpModel[MleapContext, PcaModel] {
    override val klazz: Class[PcaModel] = classOf[PcaModel]

    override def opName: String = Bundle.BuiltinOps.feature.pca

    override def store(model: Model, obj: PcaModel)
                      (implicit context: BundleContext[MleapContext]): Model = {
      model.withAttr("principal_components", Value.tensor[Double](DenseTensor(obj.principalComponents.values,
        Seq(obj.principalComponents.numRows, obj.principalComponents.numCols))))
    }

    override def load(model: Model)
                     (implicit context: BundleContext[MleapContext]): PcaModel = {
      val values = model.value("principal_components").getTensor[Double]
      PcaModel(new DenseMatrix(values.dimensions.head, values.dimensions(1), values.toArray))
    }
  }

  override val klazz: Class[Pca] = classOf[Pca]

  override def name(node: Pca): String = node.uid

  override def model(node: Pca): PcaModel = node.model

  override def load(node: Node, model: PcaModel)
                   (implicit context: BundleContext[MleapContext]): Pca = {
    Pca(uid = node.name,
      inputCol = node.shape.standardInput.name,
      outputCol = node.shape.standardOutput.name,
      model = model)
  }

  override def shape(node: Pca): Shape = Shape().withStandardIO(node.inputCol, node.outputCol)
}
