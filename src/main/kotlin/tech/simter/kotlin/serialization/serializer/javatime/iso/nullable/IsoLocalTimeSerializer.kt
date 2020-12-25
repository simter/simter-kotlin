package tech.simter.kotlin.serialization.serializer.javatime.iso.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractNullableJavaTimeSerializer
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME

/**
 * A [KSerializer] between [LocalTime] and string value with ISO format [ISO_LOCAL_TIME].
 *
 * Note: empty [String] value decodes to a null [LocalTime] value
 *
 * @author RJ
 */
object IsoLocalTimeSerializer : AbstractNullableJavaTimeSerializer<LocalTime?>(ISO_LOCAL_TIME) {
  override fun deserialize(decoder: Decoder): LocalTime? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else LocalTime.parse(v, formatter)
  }
}