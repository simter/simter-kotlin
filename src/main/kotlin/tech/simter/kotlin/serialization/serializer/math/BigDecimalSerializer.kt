package tech.simter.kotlin.serialization.serializer.math

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

/**
 * A [KSerializer] between [BigDecimal] and [String] or [Number].
 *
 * Note:
 * 1. Use for `Json {isLenient = true}`
 * 2. if [BigDecimal].scale() > 0 then serialize to integer number, others serialize to decimal number
 * 3. [BigDecimal] deserialize from a quoted or unquoted number or string
 *
 * @author RJ
 */
object BigDecimalSerializer : KSerializer<BigDecimal> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
    serialName = BigDecimalSerializer::class.qualifiedName!!,
    kind = PrimitiveKind.STRING
  )

  override fun deserialize(decoder: Decoder): BigDecimal {
    return BigDecimal(decoder.decodeString())
  }

  override fun serialize(encoder: Encoder, value: BigDecimal) {
    // value = value.precision() * 10^(-1 * value.scale())
    if (value.scale() > 0) encoder.encodeDouble(value.toDouble())
    else encoder.encodeLong(value.toLong())
  }
}