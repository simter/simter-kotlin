package tech.simter.kotlin.serialization.serializer.math.nullable

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * Test [BigDecimalAsStringSerializer] for nullable property.
 *
 * @author RJ
 */
class BigDecimalAsStringSerializerTest {
  @Serializable
  private data class Bean(
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val p1: BigDecimal?,
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val p2: BigDecimal? = null
  )

  @Test
  fun `serialize to string`() {
    assertThat(Json.encodeToString(Bean(
      p1 = BigDecimal("123.45"),
      p2 = null
    ))).isEqualTo(
      """{"p1":"123.45"}"""
    )

    assertThat(Json.encodeToString(Bean(
      p1 = BigDecimal("123.45"),
      p2 = BigDecimal(100)
    ))).isEqualTo(
      """{"p1":"123.45","p2":"100"}"""
    )
  }

  @Test
  fun `deserialize from string`() {
    // from decimal string
    assertThat(Json.decodeFromString<Bean>(
      """{"p1":"123.45","p2":null}"""
    )).isEqualTo(Bean(
      p1 = BigDecimal("123.45"),
      p2 = null
    ))
    // from integer string
    assertThat(Json.decodeFromString<Bean>(
      """{"p1":"123","p2":null}"""
    )).isEqualTo(Bean(
      p1 = BigDecimal(123),
      p2 = null
    ))

    // from empty string
    assertThat(Json.decodeFromString<Bean>(
      """{"p1":"","p2":null}"""
    )).isEqualTo(Bean(
      p1 = null,
      p2 = null
    ))
  }
}