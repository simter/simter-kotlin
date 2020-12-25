package tech.simter.kotlin.serialization.serializer.javatime.iso.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractNullableJavaTimeSerializer
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

/**
 * A [KSerializer] between [LocalDate] and string value with ISO format [ISO_LOCAL_DATE].
 *
 * Note: empty [String] value decodes to a null [LocalDate] value
 *
 * @author RJ
 */
object IsoLocalDateSerializer : AbstractNullableJavaTimeSerializer<LocalDate?>(ISO_LOCAL_DATE) {
  override fun deserialize(decoder: Decoder): LocalDate? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else LocalDate.parse(v, formatter)
  }
}