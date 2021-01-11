package tech.simter.kotlin.serialization

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Directly serialize Serializable bean.
 */
@SpringJUnitConfig(KotlinJsonAutoConfiguration::class)
class KotlinJsonAutoConfiguration1Test @Autowired constructor(private val json: Json) {
  @Serializable
  data class Bean(
    val id: Int,
    val code: String,
    val name: String? = null,
    @Contextual
    val bd: BigDecimal? = null,
    @Contextual
    val ld: LocalDate? = null,
    @Contextual
    val ldt: LocalDateTime? = null,
    val ldtList: List<@Contextual LocalDateTime>? = null
  )

  @Test
  fun `serialize all`() {
    val ldt = LocalDateTime.of(2021, 1, 31, 8, 10, 30)
    assertThat(json.encodeToString(Bean(
      id = 0,
      code = "c",
      name = "n",
      bd = BigDecimal("100.01"),
      ld = LocalDate.of(2021, 1, 31),
      ldt = ldt,
      ldtList = listOf(ldt)
    )))
      .isEqualTo("""{"id":0,"code":"c","name":"n","bd":100.01,"ld":"2021-01-31","ldt":"2021-01-31 08:10:30""""+
      ""","ldtList":["2021-01-31 08:10:30"]}""")

    assertThat(json.encodeToString(Bean(
      id = 0,
      code = "c",
      name = "n",
      ld = LocalDate.of(2021, 1, 31),
      ldt = ldt,
      ldtList = emptyList()
    )))
      .isEqualTo("""{"id":0,"code":"c","name":"n","ld":"2021-01-31","ldt":"2021-01-31 08:10:30""""+
      ""","ldtList":[]}""")
  }

  @Test
  fun `default property value not serialize`() {
    assertThat(json.encodeToString(Bean(id = 0, code = "c")))
      .isEqualTo("""{"id":0,"code":"c"}""")
    assertThat(json.encodeToString(Bean(id = 0, code = "c", name = null)))
      .isEqualTo("""{"id":0,"code":"c"}""")
  }
}