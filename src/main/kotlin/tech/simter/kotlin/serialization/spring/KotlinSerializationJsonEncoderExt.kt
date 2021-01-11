package tech.simter.kotlin.serialization.spring

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.springframework.core.ResolvableType
import org.springframework.core.codec.CharSequenceEncoder
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.util.ConcurrentReferenceHashMap
import org.springframework.util.MimeType
import java.lang.reflect.Type

/**
 * Extend [KotlinSerializationJsonEncoder] to control the [Json] classDiscriminator or even remove it.
 *
 * Notes: For polymorphic, kotlin-serialization use key 'type' to indicate the real class,.
 * Its default value can be override by `@SerialName("...")`.
 * The key 'type' large probability conflicts with normal bean property name.
 * So this extension make it settable or remove it through the constructor.
 *
 * Ref:
 *
 * - [kotlinx.serialization/Polymorphism](https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md)
 * - [kotlinx.serialization/issue#1128-Configuration option to add class discriminator to all JSON Objects](https://github.com/Kotlin/kotlinx.serialization/issues/1128)
 *
 * @param[json] the [Json] instance use to encode json string
 * @param[removeClassDiscriminator] whether to remove the classDiscriminator when encode to json string, default false.
 * @param[classDiscriminator] the [Json] classDiscriminator for polymorphic, default value is kotlin-serialization 'type'
 *
 * @author RJ
 */
class KotlinSerializationJsonEncoderExt(
  private val json: Json,
  private val removeClassDiscriminator: Boolean = false,
  private val classDiscriminator: String = "type" // same with kotlin-serialization default value
) : KotlinSerializationJsonEncoder(json) {
  // copy from [org.springframework.http.codec.json.KotlinSerializationJsonEncoder]
  private val serializerCache: MutableMap<Type, KSerializer<Any>> = ConcurrentReferenceHashMap()

  //-- CharSequence encoding needed for now, see https://github.com/Kotlin/kotlinx.serialization/issues/204 for more details
  private val charSequenceEncoder = CharSequenceEncoder.allMimeTypes()

  @ExperimentalSerializationApi
  override fun encodeValue(value: Any, bufferFactory: DataBufferFactory, valueType: ResolvableType, mimeType: MimeType?, hints: MutableMap<String, Any>?): DataBuffer {
    val jsonStr = encodeToString(
      json = json,
      serializer = serializer(valueType.type),
      value = value,
      removeClassDiscriminator = removeClassDiscriminator,
      classDiscriminator = classDiscriminator
    )
    return charSequenceEncoder.encodeValue(jsonStr, bufferFactory, valueType, mimeType, null)
  }

  /** copy from [org.springframework.http.codec.json.KotlinSerializationJsonEncoder.serializer] */
  @ExperimentalSerializationApi
  private fun serializer(type: Type): KSerializer<Any> {
    var serializer = serializerCache[type]
    if (serializer == null) {
      serializer = kotlinx.serialization.serializer(type)
      serializerCache[type] = serializer
    }
    return serializer
  }

  companion object {
    /**
     * TODO Waiting kotlinx-serialization to support removeClassDiscriminator and get classDiscriminator.
     * After that this method should be removed.
     */
    fun <T> encodeToString(
      json: Json,
      serializer: SerializationStrategy<T>,
      value: T,
      removeClassDiscriminator: Boolean = false,
      classDiscriminator: String = "type"
    ): String {
      return if (!removeClassDiscriminator) json.encodeToString(serializer, value)
      else json.encodeToString(
        json.encodeToJsonElement(serializer, value)
          .jsonObject
          .filterNot { it.key == classDiscriminator } // remove class discriminator
      )
    }
  }
}