package tech.simter.kotlin.serialization.serializer.math.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

/**
 * A [KSerializer] between [BigDecimal] and [Number] value.
 *
 * Note: if [BigDecimal].scale() > 0 then serialize to integer number, others serialize to decimal number
 *
 * @author RJ
 */
object BigDecimalAsNumberSerializer : KSerializer<BigDecimal?> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
    serialName = BigDecimalAsNumberSerializer::class.qualifiedName!!,
    kind = PrimitiveKind.DOUBLE
  )

  override fun deserialize(decoder: Decoder): BigDecimal? {
    return BigDecimal(decoder.decodeDouble())
  }

  override fun serialize(encoder: Encoder, value: BigDecimal?) {
    // value = value.precision() * 10^(-1 * value.scale())
    when {
      value == null -> encoder.encodeNull()
      value.scale() > 0 -> encoder.encodeDouble(value.toDouble())
      else -> encoder.encodeLong(value.toLong())
    }
  }
}