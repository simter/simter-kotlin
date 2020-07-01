package tech.simter.kotlin.serialization.serializer.javatime.int

import kotlinx.serialization.*
import java.time.Month

object IntMonthSerializer : KSerializer<Month> {
  override val descriptor: SerialDescriptor = PrimitiveDescriptor(
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