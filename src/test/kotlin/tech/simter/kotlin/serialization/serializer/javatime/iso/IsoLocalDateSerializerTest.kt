package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

/**
 * Test [IsoLocalDateSerializer]
 *
 * @author RJ
 */
class IsoLocalDateSerializerTest {
  private val json = Json { encodeDefaults = false }

  @Serializable
  data class Bean(
    val ps: List<@Serializable(with = IsoLocalDateSerializer::class) LocalDate>,
    @Serializable(with = IsoLocalDateSerializer::class)
    val p1: LocalDate,
    @Serializable(with = IsoLocalDateSerializer::class)
    val p2: LocalDate?,
    @Serializable(with = IsoLocalDateSerializer::class)
    val p3: LocalDate? = null
  )

  @Test
  fun test() {
    val t = LocalDate.of(2019, 1, 31)
    val str = """{"ps":["2019-01-31"],"p1":"2019-01-31","p2":null}"""
    val bean = Bean(ps = listOf(t), p1 = t, p2 = null)
    assertThat(json.decodeFromString<Bean>(str)).isEqualTo(bean)
    assertThat(json.encodeToString(bean)).isEqualTo(str)
  }
}