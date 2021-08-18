package tech.simter.kotlin.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JsonExtensionTest {
  @Serializable
  private data class Bean(
    val s: String,
    val n: String?,
    val i: Int,
    val b: Boolean
  )

  private val json = Json {}
  private val bean = Bean(s = "s", n = null, i = 1, b = true)
  private val beans = listOf(Bean(s = "s", n = null, i = 1, b = true))

  @Test
  fun `exclude properties from JsonObject`() {
    val j = json.encodeToJsonElement(bean)
    assertThat(j.toString()).isEqualTo("""{"s":"s","n":null,"i":1,"b":true}""")

    assertThat(j.excludeProperties().toString()).isEqualTo("""{"s":"s","n":null,"i":1,"b":true}""")
    assertThat(j.excludeProperties("s").toString()).isEqualTo("""{"n":null,"i":1,"b":true}""")
    assertThat(j.excludeProperties("s", "n").toString()).isEqualTo("""{"i":1,"b":true}""")
  }

  @Test
  fun `exclude properties from JsonArray`() {
    val j = json.encodeToJsonElement(beans)
    assertThat(j.toString()).isEqualTo("""[{"s":"s","n":null,"i":1,"b":true}]""")

    assertThat(j.excludeProperties().toString()).isEqualTo("""[{"s":"s","n":null,"i":1,"b":true}]""")
    assertThat(j.excludeProperties("s").toString()).isEqualTo("""[{"n":null,"i":1,"b":true}]""")
    assertThat(j.excludeProperties("s", "n").toString()).isEqualTo("""[{"i":1,"b":true}]""")
  }

  @Test
  fun `encodeToString with excludeProperties on bean`() {
    assertThat(json.encodeToString(bean)).isEqualTo("""{"s":"s","n":null,"i":1,"b":true}""")
    assertThat(json.encodeToString(bean, "s")).isEqualTo("""{"n":null,"i":1,"b":true}""")
    assertThat(json.encodeToString(bean, "s", "n")).isEqualTo("""{"i":1,"b":true}""")
  }

  @Test
  fun `encodeToString with excludeProperties on beans`() {
    assertThat(json.encodeToString(beans)).isEqualTo("""[{"s":"s","n":null,"i":1,"b":true}]""")
    assertThat(json.encodeToString(beans, "s")).isEqualTo("""[{"n":null,"i":1,"b":true}]""")
    assertThat(json.encodeToString(beans, "s", "n")).isEqualTo("""[{"i":1,"b":true}]""")
  }
}