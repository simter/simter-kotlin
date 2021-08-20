package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractJavaTimeSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

/**
 * A [KSerializer] between [LocalDateTime] and string value with common format 'yyyy-MM-dd HH:mm'.
 *
 * @author RJ
 */
object CommonLocalDateTimeToMinuteSerializer : AbstractJavaTimeSerializer<LocalDateTime>(ofPattern("yyyy-MM-dd HH:mm")) {
  override fun deserialize(decoder: Decoder): LocalDateTime {
    return LocalDateTime.parse(decoder.decodeString(), formatter)
  }
}