package tech.simter.kotlin.serialization

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * kotlinx-serialization common use test。
 *
 * @author RJ
 */
class CommonUsageTest {
  private val stableJson = Json {}

  @Serializable
  private data class Bean1(
    val p: String
  )

  // strictMode = true:
  // 1. Forbidden not property key exists in json string.
  // 2. Forbidden trailing comma.
  @Test
  fun strictRules() {
    // 1. success
    var str = """{
      "p": "test"
    }"""
    val b = stableJson.decodeFromString<Bean1>(str)
    assertThat(b.p).isEqualTo("test")

    // 2. failed
    // Unexpected JSON token at offset 45: Encountered an unknown key 'unknownKey'...
    str = """{
      "p": "test",
      "unknownKey": true
    }"""
    assertThatThrownBy { stableJson.decodeFromString<Bean1>(str) }
      .isInstanceOf(SerializationException::class.java)
      .hasMessageContaining("Encountered an unknown key 'unknownKey'")

    // 3. failed
    // JsonDecodingException: Invalid JSON at 37: Encountered an unknown key ...
    str = """{
      "p": "test",
    }"""
    assertThatThrownBy { stableJson.decodeFromString<Bean1>(str) }
      .isInstanceOf(SerializationException::class.java)
      .hasMessageContaining("Unexpected trailing comma")
  }

  @Serializable
  private data class Bean2(
    @SerialName("p")
    val name: String,
    val opt1: String? = null,
    val opt2: String = "t"
  )

  // Use  @SerialName to map a diff property name.
  // Default property allow to not exist in json string.
  @Test
  fun serialAndOptionalName() {
    val str = """{
      "p": "test"
    }"""
    val b = stableJson.decodeFromString<Bean2>(str)
    assertThat(b.name).isEqualTo("test")
    assertThat(b.opt1).isNull()
    assertThat(b.opt2).isEqualTo("t")
  }

  @Serializable
  private data class Bean3(
    val intNum: Int,
    val intNumFromStr: Int
  )

  // Can auto convert string to number
  @Test
  fun string2int() {
    val str = """{
      "intNum": 123,
      "intNumFromStr": "123"
    }"""
    val b = stableJson.decodeFromString<Bean3>(str)
    assertThat(b.intNum).isEqualTo(123)
    assertThat(b.intNumFromStr).isEqualTo(123)
  }

  @Serializable
  private data class BeanX(
    val str: String,
    val bool: Boolean,
    val intNum: Int,
    val floatNum: Float,
    val doubleNum: Double,
    val none: String? = null
  )

  // Some common types convert
  @Test
  fun commonConvert() {
    val str = """{
      "str": "test",
      "bool": true,
      "intNum": 123,
      "floatNum": 1.23,
      "doubleNum": 1.23
    }"""
    val b = stableJson.decodeFromString<BeanX>(str)
    assertThat(b.str).isEqualTo("test")
    assertThat(b.bool).isTrue()
    assertThat(b.intNum).isEqualTo(123)
    assertThat(b.floatNum).isEqualTo(1.23f)
    assertThat(b.doubleNum).isEqualTo(1.23)
  }

  // 0.20.0 not serialize computed property
  // issue to https://github.com/Kotlin/kotlinx.serialization/issues/805
  @Test
  @Disabled
  fun testComputedProperty() {
    assertThat(stableJson.encodeToString(BeanWithComputedProperty(name = "rj")))
      .isEqualTo("""{"name":"rj","computed":"--rj"}""")
  }
  
  @Serializable
  data class BeanWithComputedProperty(val name: String) {
    val computed: String
      get() = "--$name"
  }
}