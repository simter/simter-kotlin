package tech.simter.kotlin.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

/**
 * test interface serialize with polymorphic config.
 */
@SpringJUnitConfig(KotlinJsonAutoConfiguration::class, KotlinJsonAutoConfiguration2Test.Cfg::class)
class KotlinJsonAutoConfiguration2Test @Autowired constructor(private val json: Json) {
  @Configuration
  class Cfg {
    /**
     * Register serializable implementations of [Project1] interface for kotlin serialization.
     */
    @Bean
    fun project1SerializersModule(): SerializersModule {
      return SerializersModule {
        polymorphic(Project1::class) {
          subclass(Project1Impl::class)
        }
      }
    }

    /**
     * Register serializable implementations of [Project2] interface for kotlin serialization.
     */
    @Bean
    fun project2SerializersModule(): SerializersModule {
      return SerializersModule {
        polymorphic(Project2::class) {
          subclass(Project2Impl::class)
        }
      }
    }
  }

  interface Project1 {
    val id: Int
    val code: String
    val name: String?
  }

  interface Project2 {
    val id: Int
  }

  @Serializable
  @SerialName("Project1Impl")
  data class Project1Impl(override val id: Int, override val code: String, override val name: String? = null, val title: String) : Project1

  @Serializable
  @SerialName("Project2Impl")
  data class Project2Impl(override val id: Int) : Project2

  @Test
  fun `serialize all concrete properties`() {
    // for interface implementation, add type key to indicate the real class
    val p1: Project1 = Project1Impl(id = 0, code = "c", title = "t")
    assertThat(json.encodeToString(p1))
      .isEqualTo("""{"type":"Project1Impl","id":0,"code":"c","title":"t"}""")

    // for interface implementation, add type key to indicate the real class
    val p2: Project2 = Project2Impl(id = 1)
    assertThat(json.encodeToString(p2))
      .isEqualTo("""{"type":"Project2Impl","id":1}""")
  }
}