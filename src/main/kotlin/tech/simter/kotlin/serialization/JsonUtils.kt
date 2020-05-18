package tech.simter.kotlin.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

/**
 * All share kotlin json tools.
 *
 * @author RJ
 */
object JsonUtils {
  /**
   * A share stable kotlin json configuration.
   *
   * With special strict features:
   *
   * 1. ignoreUnknownKeys = true < ignore unknown keys when deserialize
   * 2. encodeDefaults = false   < property not serialize if value equals to its default value
   * 3. prettyPrint = false      < no indent and spaces between key or value
   */
  val cfg = JsonConfiguration.Stable.copy(
    ignoreUnknownKeys = true,
    encodeDefaults = false,
    prettyPrint = false,
    isLenient = true
  )

  /** A kotlin json instance with [cfg] configuration to not encode defaults and ignore unknown keys */
  val json = Json(cfg)
}