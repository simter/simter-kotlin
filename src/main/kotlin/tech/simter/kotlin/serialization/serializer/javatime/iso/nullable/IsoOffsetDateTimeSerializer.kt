package tech.simter.kotlin.serialization.serializer.javatime.iso.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractNullableJavaTimeSerializer
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME

/**
 * A [KSerializer] between [OffsetDateTime] and string value with ISO format [ISO_OFFSET_DATE_TIME].
 *
 * Note: empty [String] value decodes to a null [OffsetDateTime] value
 *
 * @author RJ
 */
object IsoOffsetDateTimeSerializer : AbstractNullableJavaTimeSerializer<OffsetDateTime?>(ISO_OFFSET_DATE_TIME) {
  override fun deserialize(decoder: Decoder): OffsetDateTime? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else OffsetDateTime.parse(v, formatter)
  }
}