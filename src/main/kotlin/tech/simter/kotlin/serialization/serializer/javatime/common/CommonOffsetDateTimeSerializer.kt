package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractJavaTimeSerializer
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*

/**
 * A [KSerializer] between [OffsetDateTime] and string value with common format 'yyyy-MM-dd HH:mm:ss+Z'.
 *
 * Always recognize time offset is local offset.
 *
 * @author RJ
 */
object CommonOffsetDateTimeSerializer : AbstractJavaTimeSerializer<OffsetDateTime>(
  DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    .appendOffsetId()
    .toFormatter(Locale.getDefault(Locale.Category.FORMAT))
) {
  override fun deserialize(decoder: Decoder): OffsetDateTime {
    return OffsetDateTime.parse(decoder.decodeString(), formatter)
  }
}