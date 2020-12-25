package tech.simter.kotlin.serialization

import kotlinx.serialization.json.Json

/**
 * All share kotlin json tools.
 *
 * @author RJ
 */
object JsonUtils {
  /**
   * Creates an instance of [Json] configured with the bellow special features:
   *
   * 1. ignoreUnknownKeys = true < ignore unknown keys when deserialize
   * 2. encodeDefaults = false   < property not serialize if value equals to its default value
   * 3. prettyPrint = false      < no indent and spaces between key or value
   * 4. isLenient = true         < allowed quoted boolean literals, and unquoted string literals
   */
  val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = false
    prettyPrint = false
    isLenient = true
  }
}