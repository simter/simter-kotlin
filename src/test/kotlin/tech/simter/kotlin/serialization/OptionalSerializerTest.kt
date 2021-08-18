@file:UseSerializers(OptionalSerializer::class)

package tech.simter.kotlin.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.simter.kotlin.serialization.serializer.OptionalSerializer
import java.util.*

/**
 * Test [OptionalSerializer].
 *
 * @author RJ
 */
class OptionalSerializerTest {
  private val json = Json { }

  @Serializable
  private data class B(
    val p1: Optional<String>,
    val p2: Optional<String> = Optional.empty()
  )

  @Test
  fun serialize() {
    assertThat(json.encodeToString(B(p1 = Optional.of("v1")))).isEqualTo("""{"p1":"v1"}""")
    assertThat(json.encodeToString(B(p1 = Optional.empty()))).isEqualTo("""{"p1":null}""")
  }

  @Test
  fun deserialize() {
    //assertThat(json.decodeFromString<B>("""{"p1":"v1"}""")).isEqualTo(B(p1 = Optional.of("v1")))
    assertThat(json.decodeFromString<B>("""{"p1":null}""")).isEqualTo(B(p1 = Optional.empty()))
  }
}