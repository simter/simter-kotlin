package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset

/**
 * Test [CommonOffsetDateTimeSerializer]
 *
 * @author RJ
 */
class CommonOffsetDateTimeSerializerTest {
  private val json = Json { encodeDefaults = false }

  @Serializable
  data class Bean(
    val ps: List<@Serializable(with = CommonOffsetDateTimeSerializer::class) OffsetDateTime>,
    @Serializable(with = CommonOffsetDateTimeSerializer::class)
    val p1: OffsetDateTime,
    @Serializable(with = CommonOffsetDateTimeSerializer::class)
    val p2: OffsetDateTime?,
    @Serializable(with = CommonOffsetDateTimeSerializer::class)
    val p3: OffsetDateTime? = null
  )

  @Test
  fun test() {
    val t = OffsetDateTime.of(2019, 1, 31, 1, 20, 59, 0, ZoneOffset.ofHours(8))
    val str = """{"ps":["2019-01-31 01:20:59+08:00"],"p1":"2019-01-31 01:20:59+08:00","p2":null}"""
    val bean = Bean(ps = listOf(t), p1 = t, p2 = null)
    assertThat(json.decodeFromString<Bean>(str)).isEqualTo(bean)
    assertThat(json.encodeToString(bean)).isEqualTo(str)
  }
}