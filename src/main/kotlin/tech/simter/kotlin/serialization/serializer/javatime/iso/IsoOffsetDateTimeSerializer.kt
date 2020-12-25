package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractJavaTimeSerializer
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME

/**
 * A [KSerializer] between [OffsetDateTime] and string value with ISO format [ISO_OFFSET_DATE_TIME].
 *
 * @author RJ
 */
object IsoOffsetDateTimeSerializer : AbstractJavaTimeSerializer<OffsetDateTime>(ISO_OFFSET_DATE_TIME) {
  override fun deserialize(decoder: Decoder): OffsetDateTime {
    return OffsetDateTime.parse(decoder.decodeString(), formatter)
  }
}