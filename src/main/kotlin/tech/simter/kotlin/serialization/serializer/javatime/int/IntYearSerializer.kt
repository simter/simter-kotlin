package tech.simter.kotlin.serialization.serializer.javatime.int

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Year

/**
 * A [KSerializer] between [Year] and [Int] value.
 *
 * @author RJ
 */
object IntYearSerializer : KSerializer<Year> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
    serialName = IntYearSerializer::class.qualifiedName!!,
    kind = PrimitiveKind.INT
  )

  override fun deserialize(decoder: Decoder): Year {
    return Year.of(decoder.decodeInt())
  }

  override fun serialize(encoder: Encoder, value: Year) {
    encoder.encodeInt(value.value)
  }
}