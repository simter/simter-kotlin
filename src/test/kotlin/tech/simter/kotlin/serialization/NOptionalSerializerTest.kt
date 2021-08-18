package tech.simter.kotlin.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.simter.kotlin.beans.NOptional
import tech.simter.kotlin.serialization.serializer.NOptionalSerializer

/**
 * Test [NOptionalSerializer].
 *
 * @author RJ
 */
class NOptionalSerializerTest {
  private val json = Json {}

  @Serializable
  private data class B(
    @Serializable(with = NOptionalSerializer::class)
    val p1: NOptional<String>
  )

  @Test
  fun `test serialize`() {
    assertThat(json.encodeToString(B(p1 = NOptional.of("v1")))).isEqualTo("""{"p1":"v1"}""")
  }

  @Test
  fun `test deserialize`() {
    assertThat(json.decodeFromString<B>("""{"p1":"v1"}""")).isEqualTo(B(p1 = NOptional.of("v1")))
  }
}