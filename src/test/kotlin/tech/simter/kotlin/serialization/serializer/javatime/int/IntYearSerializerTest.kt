package tech.simter.kotlin.serialization.serializer.javatime.int

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Year

/**
 * Test [IntYearSerializer]
 *
 * @author RJ
 */
class IntIntYearSerializerTest {
  private val json = Json { encodeDefaults = false }

  @Serializable
  data class Bean(
    val ps: List<@Serializable(with = IntYearSerializer::class) Year>,
    @Serializable(with = IntYearSerializer::class)
    val p1: Year,
    @Serializable(with = IntYearSerializer::class)
    val p2: Year?,
    @Serializable(with = IntYearSerializer::class)
    val p3: Year? = null
  )

  @Test
  fun test() {
    val year = Year.of(2019)
    val str = """{"ps":[2019],"p1":2019,"p2":null}"""
    val bean = Bean(ps = listOf(year), p1 = year, p2 = null)
    assertThat(json.decodeFromString<Bean>(str)).isEqualTo(bean)
    assertThat(json.encodeToString(bean)).isEqualTo(str)
  }
}