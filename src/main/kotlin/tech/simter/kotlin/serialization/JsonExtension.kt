package tech.simter.kotlin.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

/**
 * Encode [value] to json-string without [excludeProperties].
 *
 * Note: only effect on final [JsonObject] or [JsonArray] element type. [JsonArray] element would be dealt recursively.
 *
 * @author RJ
 */
inline fun <reified T> Json.encodeToString(value: T, vararg excludeProperties: String): String {
  return if (excludeProperties.isEmpty()) this.encodeToString(value)
  else this.encodeToJsonElement(value).excludeProperties(*excludeProperties).toString()
}

/**
 * Refactor this [JsonElement] to without [excludeProperties].
 *
 * Note: only effect on [JsonObject] or [JsonArray] element type. [JsonArray] element would be dealt recursively.
 *
 * @author RJ
 */
fun JsonElement.excludeProperties(vararg excludeProperties: String): JsonElement {
  return if (excludeProperties.isEmpty()) this
  else when (this) {
    is JsonObject -> JsonObject(this.filterNot { excludeProperties.contains(it.key) })
    is JsonArray -> JsonArray(this.map { it.excludeProperties(*excludeProperties) })
    else -> this
  }
}
