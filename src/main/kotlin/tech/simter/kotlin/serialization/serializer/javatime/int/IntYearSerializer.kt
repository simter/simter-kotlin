package tech.simter.kotlin.serialization.serializer.javatime.int

import kotlinx.serialization.*
import java.time.Year

object IntYearSerializer : KSerializer<Year> {
  override val descriptor: SerialDescriptor = PrimitiveDescriptor(
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