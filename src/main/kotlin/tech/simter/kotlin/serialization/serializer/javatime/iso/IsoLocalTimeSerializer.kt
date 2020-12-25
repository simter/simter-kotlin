package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractJavaTimeSerializer
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME

/**
 * A [KSerializer] between [LocalTime] and string value with ISO format [ISO_LOCAL_TIME].
 *
 * @author RJ
 */
object IsoLocalTimeSerializer : AbstractJavaTimeSerializer<LocalTime>(ISO_LOCAL_TIME) {
  override fun deserialize(decoder: Decoder): LocalTime {
    return LocalTime.parse(decoder.decodeString(), formatter)
  }
}