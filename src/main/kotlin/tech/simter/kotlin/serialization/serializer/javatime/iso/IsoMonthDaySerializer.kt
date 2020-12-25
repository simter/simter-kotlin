package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractJavaTimeSerializer
import java.time.MonthDay
import java.time.format.DateTimeFormatter.ofPattern

/**
 * A [KSerializer] between [MonthDay] and string value with format 'MM-dd'.
 *
 * @author RJ
 */
object IsoMonthDaySerializer : AbstractJavaTimeSerializer<MonthDay>(ofPattern("MM-dd")) {
  override fun deserialize(decoder: Decoder): MonthDay {
    return MonthDay.parse(decoder.decodeString(), formatter)
  }
}