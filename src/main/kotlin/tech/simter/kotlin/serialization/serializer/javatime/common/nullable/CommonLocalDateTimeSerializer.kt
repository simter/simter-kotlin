package tech.simter.kotlin.serialization.serializer.javatime.common.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractNullableJavaTimeSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

/**
 * A [KSerializer] between [LocalDateTime] and string value with common format 'yyyy-MM-dd HH:mm:ss'.
 *
 * Note: empty [String] value decodes to a null [LocalDateTime] value
 *
 * @author RJ
 */
object CommonLocalDateTimeSerializer :
  AbstractNullableJavaTimeSerializer<LocalDateTime?>(ofPattern("yyyy-MM-dd HH:mm:ss")) {
  override fun deserialize(decoder: Decoder): LocalDateTime? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else LocalDateTime.parse(v, formatter)
  }
}