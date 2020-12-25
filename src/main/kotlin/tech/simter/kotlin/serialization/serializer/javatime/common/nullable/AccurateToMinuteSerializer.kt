package tech.simter.kotlin.serialization.serializer.javatime.common.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.time.temporal.Temporal

/**
 * A [KSerializer] just for serialize javatime to format 'yyyy-MM-dd HH:mm' or 'HH:mm'.
 *
 * - Support [LocalDateTime], [OffsetDateTime], [ZonedDateTime], [LocalTime], [OffsetTime].
 * - Not support deserialize。
 *
 * @author RJ
 */
object AccurateToMinuteSerializer : KSerializer<Temporal?> {
  private val dateTimeFormatter: DateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm")
  private val timeFormatter: DateTimeFormatter = ofPattern("HH:mm")
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
    serialName = "javatime.common.nullable.AccurateToMinuteSerializer",
    kind = PrimitiveKind.STRING
  )

  override fun serialize(encoder: Encoder, value: Temporal?) {
    if (value == null) encoder.encodeNull()
    else {
      val formatter = when (value::class) {
        LocalDateTime::class -> dateTimeFormatter
        OffsetDateTime::class -> dateTimeFormatter
        ZonedDateTime::class -> dateTimeFormatter
        LocalTime::class -> timeFormatter
        OffsetTime::class -> timeFormatter
        else -> throw UnsupportedOperationException("Not support serialize type ${value.javaClass.name}")
      }
      encoder.encodeString(formatter.format(value))
    }
  }

  override fun deserialize(decoder: Decoder): Temporal? {
    throw UnsupportedOperationException("AccurateToMinuteSerializer not support deserialize")
  }
}