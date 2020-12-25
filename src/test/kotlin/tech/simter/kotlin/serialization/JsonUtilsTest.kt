package tech.simter.kotlin.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * kotlinx-serialization common use test。
 *
 * @author RJ
 */
class JsonUtilsTest {
  @Test
  fun testJson() {
    @Serializable
    data class Bean(
      val p1: String,
      @SerialName("cp2")
      val p2: String,
      val p3: String?,
      val p4: String? = null,
      val p5: String = "v5",
      val p6: String = "v6",
      @Transient
      val p7: String = "v7"
    ) {
      val p9: String = "v9"
      val p8: String
        get() = p1
    }

    assertThat(JsonUtils.json.encodeToString(
      Bean(
        p1 = "v1",
        p2 = "v2", // custom serial name to cp2
        p3 = null, // set null value
        p4 = null, // set value equals to defaults
        p5 = "v5-" // set value not equals to defaults
        // p6      // not set the default value
        // p7      // not serial
      )
    )).isEqualTo("""{"p1":"v1","cp2":"v2","p3":null,"p5":"v5-"}""")
  }
}