package tech.simter.kotlin.serialization.serializer.math.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

/**
 * A [KSerializer] between [BigDecimal] and digit number.
 *
 * Note:
 * 1. Use for `Json {isLenient = true}`
 * 2. if [BigDecimal].scale() > 0 then serialize to integer number, others serialize to decimal number
 * 3. null [BigDecimal] serialize to null value
 * 4. empty [String] value deserialize to a null [BigDecimal] value
 * 5. [BigDecimal] deserialize from a quoted or unquoted number or string
 *
 * @author RJ
 */
object BigDecimalSerializer : KSerializer<BigDecimal?> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
    serialName = BigDecimalSerializer::class.qualifiedName!!,
    kind = PrimitiveKind.STRING
  )

  override fun deserialize(decoder: Decoder): BigDecimal? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else BigDecimal(v)
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