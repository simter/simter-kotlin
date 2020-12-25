package tech.simter.kotlin.serialization.serializer.javatime.int

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Month

/**
 * A [KSerializer] between [Month] and [Int] value.
 *
 * @author RJ
 */
object IntMonthSerializer : KSerializer<Month> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
    serialName = IntMonthSerializer::class.qualifiedName!!,
    kind = PrimitiveKind.INT
  )

  override fun deserialize(decoder: Decoder): Month {
    return Month.of(decoder.decodeInt())
  }

  override fun serialize(encoder: Encoder, value: Month) {
    encoder.encodeInt(value.value)
  }
}