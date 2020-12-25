package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.MonthDay

/**
 * Test [IsoMonthDaySerializer]
 *
 * @author RJ
 */
class IsoMonthDaySerializerTest {
  private val json = Json { encodeDefaults = false }

  @Serializable
  data class Bean(
    val ps: List<@Serializable(with = IsoMonthDaySerializer::class) MonthDay>,
    @Serializable(with = IsoMonthDaySerializer::class)
    val p1: MonthDay,
    @Serializable(with = IsoMonthDaySerializer::class)
    val p2: MonthDay?,
    @Serializable(with = IsoMonthDaySerializer::class)
    val p3: MonthDay? = null
  )

  @Test
  fun test() {
    val md = MonthDay.of(1, 31)
    val str = """{"ps":["01-31"],"p1":"01-31","p2":null}"""
    val bean = Bean(ps = listOf(md), p1 = md, p2 = null)
    assertThat(json.decodeFromString<Bean>(str)).isEqualTo(bean)
    assertThat(json.encodeToString(bean)).isEqualTo(str)
  }
}