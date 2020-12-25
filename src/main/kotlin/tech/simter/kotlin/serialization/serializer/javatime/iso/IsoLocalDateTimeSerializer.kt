package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractJavaTimeSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

/**
 * A [KSerializer] between [LocalDateTime] and string value with ISO format [ISO_LOCAL_DATE_TIME].
 *
 * @author RJ
 */
object IsoLocalDateTimeSerializer : AbstractJavaTimeSerializer<LocalDateTime>(ISO_LOCAL_DATE_TIME) {
  override fun deserialize(decoder: Decoder): LocalDateTime {
    return LocalDateTime.parse(decoder.decodeString(), formatter)
  }
}