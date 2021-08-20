package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

/**
 * Test [CommonLocalDateTimeToMinuteSerializer]
 *
 * @author RJ
 */
class CommonLocalDateTimeToMinuteSerializerTest {
  private val json = Json { encodeDefaults = false }

  @Serializable
  data class Bean(
    val ps: List<@Serializable(with = CommonLocalDateTimeToMinuteSerializer::class) LocalDateTime>,
    @Serializable(with = CommonLocalDateTimeToMinuteSerializer::class)
    val p1: LocalDateTime,
    @Serializable(with = CommonLocalDateTimeToMinuteSerializer::class)
    val p2: LocalDateTime?,
    @Serializable(with = CommonLocalDateTimeToMinuteSerializer::class)
    val p3: LocalDateTime? = null
  )

  @Test
  fun test() {
    val t = LocalDateTime.of(2019, 1, 31, 1, 20)
    val str = """{"ps":["2019-01-31 01:20"],"p1":"2019-01-31 01:20","p2":null}"""
    val bean = Bean(ps = listOf(t), p1 = t, p2 = null)
    assertThat(json.decodeFromString<Bean>(str)).isEqualTo(bean)
    assertThat(json.encodeToString(bean)).isEqualTo(str)
  }
}