package tech.simter.kotlin.serialization.serializer.javatime.iso.nullable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import tech.simter.kotlin.serialization.serializer.javatime.AbstractNullableJavaTimeSerializer
import java.time.YearMonth
import java.time.format.DateTimeFormatter.ofPattern

/**
 * A [KSerializer] between [YearMonth] and string value with format 'yyyy-MM'.
 *
 * Note: empty [String] value decodes to a null [YearMonth] value
 *
 * @author RJ
 */
object IsoYearMonthSerializer
  : AbstractNullableJavaTimeSerializer<YearMonth?>(ofPattern("yyyy-MM")) {
  override fun deserialize(decoder: Decoder): YearMonth? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else YearMonth.parse(v, formatter)
  }
}