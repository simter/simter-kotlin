// See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/custom_serializers.md#useserializers-annotation
// A file-level annotation `UseSerializers` instructs all properties of the Serializer's type
// in all classes in this file would be serialized with these Serializers.
@file:UseSerializers(
  IsoOffsetDateTimeSerializer::class,
  IsoLocalDateTimeSerializer::class,
  IsoLocalDateSerializer::class,
  IsoLocalTimeSerializer::class,
  IsoMonthDaySerializer::class,
  IntMonthSerializer::class,
  IsoYearMonthSerializer::class,
  IntYearSerializer::class
)

package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.simter.kotlin.serialization.serializer.javatime.int.IntMonthSerializer
import tech.simter.kotlin.serialization.serializer.javatime.int.IntYearSerializer
import java.time.*

/**
 * Test file-level annotation [UseSerializers].
 *
 * @author RJ
 */
class FileLevelUseSerializersTest {
  private val json = Json(Stable)

  @Serializable
  data class Bean(
    val p1: LocalDateTime,
    val p2: LocalDate,
    val p3: LocalTime,
    val p4: MonthDay,
    val p5: Month,
    val p6: YearMonth,
    val p7: Year,
    val p8: OffsetDateTime
  )

  @Test
  fun test() {
    val t = OffsetDateTime.of(2019, 1, 31, 1, 20, 59, 0, ZoneOffset.ofHours(2))
    val str = """{
      "p1": "2019-01-31T01:20:59",
      "p2": "2019-01-31",
      "p3": "01:20:59",
      "p4": "01-31",
      "p5": 1,
      "p6": "2019-01",
      "p7": 2019,
      "p8": "2019-01-31T01:20:59+02:00"
    }""".replace(" ", "")
      .replace("\r\n", "")
      .replace("\r", "")
      .replace("\n", "")
    val bean = Bean(
      p1 = t.toLocalDateTime(),
      p2 = t.toLocalDate(),
      p3 = t.toLocalTime(),
      p4 = MonthDay.from(t),
      p5 = t.month,
      p6 = YearMonth.from(t),
      p7 = Year.from(t),
      p8 = t
    )
    assertThat(json.parse(Bean.serializer(), str)).isEqualTo(bean)
    assertThat(json.stringify(Bean.serializer(), bean)).isEqualTo(str)
  }
}