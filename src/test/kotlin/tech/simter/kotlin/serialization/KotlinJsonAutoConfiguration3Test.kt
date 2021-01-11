package tech.simter.kotlin.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

/**
 * test interface serialize without polymorphic config.
 */
@SpringJUnitConfig(KotlinJsonAutoConfiguration::class, KotlinJsonAutoConfiguration2Test.Cfg::class)
class KotlinJsonAutoConfiguration3Test @Autowired constructor(private val json: Json) {
  interface Project {
    val id: Int
  }

  @Serializable
  @SerialName("MyProject")
  data class MyProject(override val id: Int) : Project

  @Test
  fun `without polymorphic config exception`() {
    val p: Project = MyProject(id = 0)
    assertThatExceptionOfType(SerializationException::class.java)
      .isThrownBy { json.encodeToString(p) }
      .withMessageContaining("Class 'MyProject' is not registered for polymorphic serialization in the scope of 'Project'.")
  }
}