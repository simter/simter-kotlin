package tech.simter.kotlin.serialization.serializer.math

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * Test [BigDecimalSerializer] for none-null and nullable property.
 *
 * @author RJ
 */
class BigDecimalSerializerTest {
  // need to set isLenient to true
  private val json = Json { isLenient = true }

  @Serializable
  private data class Bean(
    @Serializable(with = BigDecimalSerializer::class)
    val p11: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val p12: BigDecimal = BigDecimal("123.45"),
    @Serializable(with = BigDecimalSerializer::class)
    val p21: BigDecimal?,
    @Serializable(with = BigDecimalSerializer::class)
    val p22: BigDecimal? = null
  )

  @Test
  fun `serialize to number`() {
    assertThat(json.encodeToString(Bean(
      p11 = BigDecimal("123.45"),
      p21 = null
    ))).isEqualTo(
      """{"p11":123.45,"p21":null}"""
    )

    assertThat(json.encodeToString(Bean(
      p11 = BigDecimal("123.45"),
      p21 = BigDecimal(100)
    ))).isEqualTo(
      """{"p11":123.45,"p21":100}"""
    )
  }

  @Test
  fun `deserialize from string or number`() {
    assertThat(json.decodeFromString<Bean>(
      """{"p11":"123.45","p21":null}"""
    )).isEqualTo(Bean(
      p11 = BigDecimal("123.45"),
      p21 = null
    ))

    assertThat(json.decodeFromString<Bean>(
      """{"p11":100.00,"p21":null}"""
    )).isEqualTo(Bean(
      p11 = BigDecimal("100.00"),
      p21 = null
    ))

    assertThat(json.decodeFromString<Bean>(
      """{"p11":"123.45","p12":"100","p21":100.45,"p22":10}"""
    )).isEqualTo(Bean(
      p11 = BigDecimal("123.45"),
      p12 = BigDecimal(100),
      p21 = BigDecimal("100.45"),
      p22 = BigDecimal(10)
    ))
  }
}