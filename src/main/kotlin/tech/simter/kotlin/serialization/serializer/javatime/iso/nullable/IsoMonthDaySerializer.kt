package tech.simter.kotlin.serialization.serializer.javatime.iso.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractNullableJavaTimeSerializer
import java.time.MonthDay
import java.time.format.DateTimeFormatter.ofPattern

/**
 * A [KSerializer] between [MonthDay] and string value with format 'MM-dd'.
 *
 * Note: empty [String] value decodes to a null [MonthDay] value
 *
 * @author RJ
 */
object IsoMonthDaySerializer : AbstractNullableJavaTimeSerializer<MonthDay?>(ofPattern("MM-dd")) {
  override fun deserialize(decoder: Decoder): MonthDay? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else MonthDay.parse(v, formatter)
  }
}