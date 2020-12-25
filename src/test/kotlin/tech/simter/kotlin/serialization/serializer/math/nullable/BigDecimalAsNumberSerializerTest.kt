package tech.simter.kotlin.serialization.serializer.math.nullable

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * Test [BigDecimalAsNumberSerializer] for nullable property.
 *
 * @author RJ
 */
class BigDecimalAsNumberSerializerTest {
  @Serializable
  private data class Bean(
    @Serializable(with = BigDecimalAsNumberSerializer::class)
    val p1: BigDecimal?,
    @Serializable(with = BigDecimalAsNumberSerializer::class)
    val p2: BigDecimal? = null
  )

  @Test
  fun `serialize to number`() {
    assertThat(Json.encodeToString(Bean(
      p1 = BigDecimal("123.45"),
      p2 = null
    ))).isEqualTo(
      """{"p1":123.45}"""
    )

    assertThat(Json.encodeToString(Bean(
      p1 = BigDecimal("123.45"),
      p2 = BigDecimal(100)
    ))).isEqualTo(
      """{"p1":123.45,"p2":100}"""
    )
  }

  @Test
  fun `deserialize from number`() {
    // from integer number
    assertThat(Json.decodeFromString<Bean>(
      """{"p1":123,"p2":null}"""
    )).isEqualTo(Bean(
      p1 = BigDecimal(123),
      p2 = null
    ))

    // from decimal number
    assertThat(Json.decodeFromString<Bean>(
      """{"p1":123.45,"p2":null}"""
    )).isEqualTo(Bean(
      p1 = BigDecimal(123.45),
      p2 = null
    ))
  }
}