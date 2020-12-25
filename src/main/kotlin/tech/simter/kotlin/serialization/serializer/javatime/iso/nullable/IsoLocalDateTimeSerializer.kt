package tech.simter.kotlin.serialization.serializer.javatime.iso.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractNullableJavaTimeSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

/**
 * A [KSerializer] between [LocalDateTime] and string value with ISO format [ISO_LOCAL_DATE_TIME].
 *
 * Note: empty [String] value decodes to a null [LocalDateTime] value
 *
 * @author RJ
 */
object IsoLocalDateTimeSerializer : AbstractNullableJavaTimeSerializer<LocalDateTime?>(ISO_LOCAL_DATE_TIME) {
  override fun deserialize(decoder: Decoder): LocalDateTime? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else LocalDateTime.parse(v, formatter)
  }
}