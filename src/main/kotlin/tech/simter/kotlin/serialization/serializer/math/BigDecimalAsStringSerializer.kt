package tech.simter.kotlin.serialization.serializer.math

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

/**
 * A [KSerializer] between [BigDecimal] and [String] value.
 *
 * Note: empty [String] value decodes to a [BigDecimal.ZERO] value
 *
 * @author RJ
 */
object BigDecimalAsStringSerializer : KSerializer<BigDecimal> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
    serialName = BigDecimalAsStringSerializer::class.qualifiedName!!,
    kind = PrimitiveKind.STRING
  )

  override fun deserialize(decoder: Decoder): BigDecimal {
    val v = decoder.decodeString()
    return if (v.isEmpty()) BigDecimal.ZERO else BigDecimal(v)
  }

  override fun serialize(encoder: Encoder, value: BigDecimal) {
    encoder.encodeString(value.toString())
  }
}