package com.truecar.mleap.runtime.transformer

import com.truecar.mleap.core.feature.Tokenizer
import com.truecar.mleap.runtime.attribute.{AttributeSchema, OtherAttribute}
import com.truecar.mleap.runtime.transformer.builder.TransformBuilder
import com.truecar.mleap.runtime.types.{ListType, StringType}
import com.truecar.mleap.runtime.transformer.builder.TransformBuilder.Ops

import scala.util.Try

/**
  * Created by hwilkins on 12/30/15.
  */
case class TokenizerModel(uid: String = Transformer.uniqueName("tokenizer"),
                          inputCol: String,
                          outputCol: String) extends Transformer {
  override def build[TB: TransformBuilder](builder: TB): Try[TB] = {
    builder.withInput(inputCol, StringType).flatMap {
      case (b, inputIndex) =>
        b.withOutput(outputCol, ListType(StringType))(row => Tokenizer.defaultTokenizer(row.getString(inputIndex)))
    }
  }

  override def transformAttributeSchema(schema: AttributeSchema): AttributeSchema = schema.withField(outputCol, OtherAttribute())
}
