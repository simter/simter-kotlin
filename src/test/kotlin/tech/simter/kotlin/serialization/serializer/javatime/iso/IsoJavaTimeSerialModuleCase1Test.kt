package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.*

/**
 * Test [IsoJavaTimeSerialModule].
 *
 * @author RJ
 */
class IsoJavaTimeSerialModuleCase1Test {
  // See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/custom_serializers.md#contextualserialization-annotation
  private val json = Json(configuration = Stable, context = IsoJavaTimeSerialModule)

  @Serializable
  data class Bean(
    @ContextualSerialization
    val p1: LocalDateTime,
    @ContextualSerialization
    val p2: LocalDate,
    @ContextualSerialization
    val p3: LocalTime,
    @ContextualSerialization
    val p4: MonthDay,
    @ContextualSerialization
    val p5: Month,
    @ContextualSerialization
    val p6: YearMonth,
    @ContextualSerialization
    val p7: Year
  )

  @Test
  fun test() {
    val t = LocalDateTime.of(2019, 1, 31, 1, 20, 59)
    val str = """{
      "p1": "2019-01-31T01:20:59",
      "p2": "2019-01-31",
      "p3": "01:20:59",
      "p4": "01-31",
      "p5": 1,
      "p6": "2019-01",
      "p7": 2019
    }""".replace(" ", "")
      .replace("\r\n", "")
      .replace("\r", "")
      .replace("\n", "")
    val bean = Bean(
      p1 = t,
      p2 = t.toLocalDate(),
      p3 = t.toLocalTime(),
      p4 = MonthDay.from(t),
      p5 = t.month,
      p6 = YearMonth.from(t),
      p7 = Year.from(t)
    )
    assertThat(json.parse(Bean.serializer(), str)).isEqualTo(bean)
    assertThat(json.stringify(Bean.serializer(), bean)).isEqualTo(str)
  }
}