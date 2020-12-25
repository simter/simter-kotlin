package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.*

/**
 * Test [CommonJavaTimeSerialModule]
 *
 * @author RJ
 */
class CommonJavaTimeSerialModuleTest {
  // See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/custom_serializers.md#contextualserialization-annotation
  private val json = Json { serializersModule = CommonJavaTimeSerialModule }

  @Serializable
  data class Bean(
    @Contextual
    val p1: LocalDateTime,
    @Contextual
    val p2: LocalDate,
    @Contextual
    val p3: LocalTime,
    @Contextual
    val p4: MonthDay,
    @Contextual
    val p5: Month,
    @Contextual
    val p6: YearMonth,
    @Contextual
    val p7: Year,
    @Contextual
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
      .replace("T", " ")
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
    assertThat(json.decodeFromString<Bean>(str)).isEqualTo(bean)
    assertThat(json.encodeToString(bean)).isEqualTo(str)
  }
}