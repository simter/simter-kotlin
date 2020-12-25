package tech.simter.kotlin.serialization.serializer.math

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * Test [BigDecimalAsStringSerializer] for none-null and nullable property.
 *
 * @author RJ
 */
class BigDecimalAsStringSerializerTest {
  @Serializable
  private data class Bean(
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val p11: BigDecimal,
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val p12: BigDecimal = BigDecimal("123.45"),
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val p21: BigDecimal?,
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val p22: BigDecimal? = null
  )

  @Test
  fun `serialize to string`() {
    assertThat(Json.encodeToString(Bean(
      p11 = BigDecimal("123.45"),
      p21 = null
    ))).isEqualTo(
      """{"p11":"123.45","p21":null}"""
    )

    assertThat(Json.encodeToString(Bean(
      p11 = BigDecimal("123.45"),
      p21 = BigDecimal("100.45")
    ))).isEqualTo(
      """{"p11":"123.45","p21":"100.45"}"""
    )

    assertThat(Json.encodeToString(Bean(
      p11 = BigDecimal("100.00"),
      p21 = BigDecimal("100")
    ))).isEqualTo(
      """{"p11":"100.00","p21":"100"}"""
    )
  }

  @Test
  fun `deserialize from String`() {
    assertThat(Json.decodeFromString<Bean>(
      """{"p11":"123.45","p21":null}"""
    )).isEqualTo(Bean(
      p11 = BigDecimal("123.45"),
      p21 = null
    ))

    assertThat(Json.decodeFromString<Bean>(
      """{"p11":"123.45","p21":"100.45"}"""
    )).isEqualTo(Bean(
      p11 = BigDecimal("123.45"),
      p21 = BigDecimal("100.45")
    ))

    assertThat(Json.decodeFromString<Bean>(
      """{"p11":"100.00","p21":"100"}"""
    )).isEqualTo(Bean(
      p11 = BigDecimal("100.00"),
      p21 = BigDecimal("100")
    ))
  }
}