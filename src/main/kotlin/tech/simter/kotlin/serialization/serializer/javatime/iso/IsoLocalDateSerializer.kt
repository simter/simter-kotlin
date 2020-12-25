package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractJavaTimeSerializer
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

/**
 * A [KSerializer] between [LocalDate] and string value with ISO format [ISO_LOCAL_DATE].
 *
 * @author RJ
 */
object IsoLocalDateSerializer : AbstractJavaTimeSerializer<LocalDate>(ISO_LOCAL_DATE) {
  override fun deserialize(decoder: Decoder): LocalDate {
    return LocalDate.parse(decoder.decodeString(), formatter)
  }
}