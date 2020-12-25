package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractJavaTimeSerializer
import java.time.YearMonth
import java.time.format.DateTimeFormatter.ofPattern

/**
 * A [KSerializer] between [YearMonth] and string value with format 'yyyy-MM'.
 *
 * @author RJ
 */
object IsoYearMonthSerializer
  : AbstractJavaTimeSerializer<YearMonth>(ofPattern("yyyy-MM")) {
  override fun deserialize(decoder: Decoder): YearMonth {
    return YearMonth.parse(decoder.decodeString(), formatter)
  }
}