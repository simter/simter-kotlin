package tech.simter.kotlin.serialization

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.simter.kotlin.data.Page

/**
 * Test serialize [Page].
 *
 * @author RJ
 */
class PageTest {
  @Serializable
  @SerialName("Bean1")
  data class Bean1(val name: String) {
    val computed: String
      get() = "--$name"
  }

  @Serializable
  @SerialName("Bean2")
  data class Bean2(val name: String)

  // https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md#polymorphism-and-generic-classes
  private val pageSerializersModule = SerializersModule {
    polymorphic(Page::class) {
      subclass(Page.Companion.Impl.serializer(PolymorphicSerializer(Any::class)))
    }
  }
  private val anyWithBean1SerializersModule = SerializersModule {
    polymorphic(Any::class) { subclass(Bean1::class) }
  }
  private val anyWithBean2SerializersModule = SerializersModule {
    polymorphic(Any::class) { subclass(Bean2::class) }
  }

  private val json = Json {
    this.classDiscriminator = "#class" // default is 'type'

    // register separate serializersModule
    this.serializersModule = pageSerializersModule
      .overwriteWith(anyWithBean1SerializersModule)
      .overwriteWith(anyWithBean2SerializersModule)

    /* Or just mix config:
    this.serializersModule = SerializersModule {
      polymorphic(Page::class) {
        // https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md#polymorphism-and-generic-classes
        subclass(Page.Companion.Impl.serializer(PolymorphicSerializer(Any::class)))
      }
      polymorphic(Any::class) {
        subclass(Bean1::class)
        subclass(Bean2::class)
      }
    }
    */
  }

  @Test
  fun emptyPage() {
    assertThat(json.encodeToString(Page.empty<String>()))
      .isEqualTo("""{"#class":"Page","offset":0,"limit":0,"total":0,"rows":[]}""")
  }

  @Test
  fun beanPage() {
    assertThat(json.encodeToString(Page.of(
      offset = 0,
      limit = 25,
      total = 1,
      rows = listOf(Bean1("n1"))
    )))
      .isEqualTo("""{"#class":"Page","offset":0,"limit":25,"total":1,"rows":[{"#class":"Bean1","name":"n1"}]}""")

    assertThat(json.encodeToString(Page.of(
      offset = 0,
      limit = 25,
      total = 1,
      rows = listOf(Bean2("n2"))
    )))
      .isEqualTo("""{"#class":"Page","offset":0,"limit":25,"total":1,"rows":[{"#class":"Bean2","name":"n2"}]}""")
  }

  @Test
  fun withoutComputedProperty() {
    assertThat(json.encodeToString(Bean1(name = "test")))
      .isEqualTo("""{"name":"test"}""")
  }
}