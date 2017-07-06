package ml.combust.mleap.runtime.transformer.feature

import ml.combust.mleap.core.types._
import org.scalatest.FunSpec

class CountVectorizerSpec extends FunSpec {

  describe("#getFields") {
    it("has the correct inputs and outputs") {
      val transformer = CountVectorizer(shape = NodeShape().
                    withStandardInput("input").
              withStandardOutput("output"), model = null)
      assert(transformer.schema.fields ==
        Seq(StructField("input", ListType(BasicType.String)),
          StructField("output", TensorType(BasicType.Double, Some(Seq(3))))))
    }
  }
}