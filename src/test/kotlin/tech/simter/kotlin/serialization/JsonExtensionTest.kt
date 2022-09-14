package tech.simter.kotlin.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class JsonExtensionTest {
  @Serializable
  private data class Bean(
    val s: String,
    val n: String?,
    val i: Int,
    val b: Boolean
  )

  val json = Json {}
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

  @Test
  fun `jsonObject to native map - default integer to Long, decimal to BigDecimal`() {
    val j = json.decodeFromString<JsonObject>(
      """
      {"n":null,"s":"s","b":true,"i1":1,"i2":1.23,"items":["a", "b"]}
      """.trimIndent()
    )
    val m = j.toNativeMap()
    assertThat(m).isEqualTo(
      mapOf(
        "n" to null,
        "s" to "s",
        "b" to true,
        "i1" to 1L,
        "i2" to BigDecimal("1.23"),
        "items" to listOf("a", "b")
      )
    )
  }

  @Test
  fun `jsonObject to native map - integer to Int, decimal to double`() {
    val j = json.decodeFromString<JsonObject>("""{"n":null,"s":"s","b":true,"i1":1,"i2":1.23}""")
    val m = j.toNativeMap(
      defaultIntegerValueMapper = TO_INT_MAPPER,
      defaultDecimalValueMapper = TO_DOUBLE_MAPPER,
    )
    assertThat(m).isEqualTo(
      mapOf(
        "n" to null,
        "s" to "s",
        "b" to true,
        "i1" to 1,
        "i2" to 1.23,
      )
    )
  }

  @Test
  fun `jsonObject to native map - with value mapper 1`() {
    val j = json.decodeFromString<JsonObject>("""{"n":null,"s":"s","b":true,"i1":1,"i2":1.23}""")
    val m = j.toNativeMap(valueMapper = mapOf(
      "n" to { "hello" },
      "i1" to { it.jsonPrimitive.int + 1 }
    ))
    assertThat(m).isEqualTo(
      mapOf(
        "n" to "hello",
        "s" to "s",
        "b" to true,
        "i1" to 2,
        "i2" to BigDecimal("1.23"),
      )
    )
  }

  @Test
  fun `jsonObject to native map - with value mapper 2`() {
    val j = json.decodeFromString<JsonObject>("""{"n":null,"items":["a","b"]}""")
    val m = j.toNativeMap(valueMapper = mapOf("items" to { listOf("c") }))
    assertThat(m).isEqualTo(
      mapOf(
        "n" to null,
        "items" to listOf("c")
      )
    )
  }

  @Test
  fun `jsonObject to native map - with value mapper 3`() {
    val j = json.decodeFromString<JsonObject>("""{"items":[{"i":1,"s":"s"}]}""")
    val m = j.toNativeMap(valueMapper = mapOf("items.s" to { "${it.jsonPrimitive.content}.m" }))
    assertThat(m).isEqualTo(
      mapOf(
        "items" to listOf(mapOf("i" to 1L, "s" to "s.m"))
      )
    )
  }
}