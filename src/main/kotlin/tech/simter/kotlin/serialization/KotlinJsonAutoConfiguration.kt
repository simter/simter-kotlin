package tech.simter.kotlin.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.simter.kotlin.serialization.serializer.javatime.common.CommonJavaTimeSerialModule
import tech.simter.kotlin.serialization.serializer.math.BigDecimalSerializer
import java.math.BigDecimal

/**
 * A [Json] auto configuration.
 *
 * Custom module can register its own [SerializersModule] through bellow sample code:
 *
 * ```
 * @Bean
 * fun mySerializersModule(): SerializersModule {
 *   return SerializersModule {
 *     polymorphic(Project::class) {
 *       subclass(MyProject1::class)
 *     }
 *   }
 * }
 * ```
 *
 * @author RJ
 */
@Configuration
@ConditionalOnClass(Json::class)
class KotlinJsonAutoConfiguration {
  private val logger: Logger = LoggerFactory.getLogger(KotlinJsonAutoConfiguration::class.java)

  companion object {
    /**
     * The class discriminator for simter project.
     *
     * Use for kotlinx-serialization to avoid conflict with the normal bean property name 'type'.
     */
    const val SIMTER_CLASS_DISCRIMINATOR: String = "_type"
  }

  /**
   * Auto register a [Json] bean to spring context.
   *
   * Notes: the next one would override the previous one.
   * Can use `@Order` to order the specific [SerializersModule].
   *
   * TODO Param [removeClassDiscriminator] not effect yet because [Json] without this configuration.
   *
   * @param[serializersModules] all register [SerializersModule]s
   * @param[removeClassDiscriminator] whether to remove the classDiscriminator when encode to json string, default false
   * @param[classDiscriminator] the [Json] classDiscriminator for polymorphic, default value is kotlin-serialization 'type'
   */
  @Bean
  fun kotlinJson(
    serializersModules: List<SerializersModule>? = emptyList(),
    @Value("\${simter.kotlinx-serialization.remove-class-discriminator:false}")
    removeClassDiscriminator: Boolean,
    @Value("\${simter.kotlinx-serialization.class-discriminator:type}")
    classDiscriminator: String = "type"
  ): Json {
    logger.info("{} SerializersModules ready to register", serializersModules?.size ?: 0)
    return Json {
      this.ignoreUnknownKeys = true
      this.encodeDefaults = false
      this.prettyPrint = false
      this.isLenient = true
      this.classDiscriminator = classDiscriminator // default value is 'type'
      if (!serializersModules.isNullOrEmpty()) {
        // mix all SerializersModule
        this.serializersModule = serializersModules.reduceIndexed { index, pre, next ->
          if (index == 0) pre
          else pre.overwriteWith(next)
        }
      }
    }
  }

  /** Register default JavaTime [SerializersModule] */
  @Bean("javaTimeSerializersModule")
  @ConditionalOnMissingBean(name = ["javaTimeSerializersModule"])
  fun javaTimeSerializersModule(): SerializersModule {
    return CommonJavaTimeSerialModule
  }

  /** Register default [BigDecimal] [SerializersModule] */
  @Bean("bigDecimalSerializersModule")
  @ConditionalOnMissingBean(name = ["bigDecimalSerializersModule"])
  fun bigDecimalSerializersModule(): SerializersModule {
    return SerializersModule {
      contextual(BigDecimal::class, BigDecimalSerializer)
    }
  }
}