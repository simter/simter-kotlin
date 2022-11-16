package tech.simter.kotlin.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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

/** Refactor this [JsonObject] to without [excludeProperties] */
fun JsonObject.excludeProperties(vararg excludeProperties: String): JsonObject {
  return if (excludeProperties.isEmpty()) this
  else JsonObject(this.filterNot { excludeProperties.contains(it.key) })
}

/** Refactor this [JsonArray] item to without [excludeProperties] */
fun JsonArray.excludeProperties(vararg excludeProperties: String): JsonArray {
  return if (excludeProperties.isEmpty()) this
  else JsonArray(this.map { it.excludeProperties(*excludeProperties) })
}

/**
 * Returns content of the current element as short
 * @throws NumberFormatException if current element is not a valid representation of number
 */
val JsonPrimitive.short: Short get() = content.toShort()

/**
 * Returns content of the current element as short or `null` if current element is not a valid representation of number
 */
val JsonPrimitive.shortOrNull: Short? get() = content.toShortOrNull()

/**
 * Returns content of the current element as [BigDecimal]
 * @throws NumberFormatException if current element is not a valid representation of number
 */
val JsonPrimitive.bigDecimal: BigDecimal get() = BigDecimal(content)

/**
 * Returns content of the current element as [BigDecimal]
 * @throws NumberFormatException if current element is not a valid representation of number
 */
val JsonPrimitive.bigDecimalOrNull: BigDecimal?
  get() = if (this is JsonNull || content.isEmpty()) null
  else BigDecimal(content)

/**
 * Returns content of the current element as ISO format [LocalDate]
 * @throws DateTimeParseException if the content cannot be parsed
 */
val JsonPrimitive.isoLocalDate: LocalDate get() = LocalDate.parse(content, DateTimeFormatter.ISO_LOCAL_DATE)

/**
 * Returns content of the current element as ISO format [LocalDate]
 * @throws DateTimeParseException if the content cannot be parsed
 */
val JsonPrimitive.isoLocalDateOrNull: LocalDate?
  get() = if (this is JsonNull || content.isEmpty()) null
  else LocalDate.parse(content, DateTimeFormatter.ISO_LOCAL_DATE)

/**
 * Returns content of the current element as ISO format [LocalDate]
 * @throws DateTimeParseException if the content cannot be parsed
 */
val JsonPrimitive.isoLocalTime: LocalTime get() = LocalTime.parse(content, DateTimeFormatter.ISO_LOCAL_TIME)

/**
 * Returns content of the current element as ISO format [LocalTime]
 * @throws DateTimeParseException if the content cannot be parsed
 */
val JsonPrimitive.isoLocalTimeOrNull: LocalTime?
  get() = if (this is JsonNull || content.isEmpty()) null
  else LocalTime.parse(content, DateTimeFormatter.ISO_LOCAL_TIME)

/**
 * Returns content of the current element as ISO format [LocalDateTime]
 * @throws DateTimeParseException if the content cannot be parsed
 */
val JsonPrimitive.isoLocalDateTime: LocalDateTime
  get() = LocalDateTime.parse(content, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

/**
 * Returns content of the current element as iso format [LocalDateTime]
 * @throws DateTimeParseException if the content cannot be parsed
 */
val JsonPrimitive.isoLocalDateTimeOrNull: LocalDateTime?
  get() = if (this is JsonNull || content.isEmpty()) null
  else LocalDateTime.parse(content, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

private val commonLocalDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

/**
 * Returns content of the current element as ISO format [LocalDateTime]
 * @throws DateTimeParseException if the content cannot be parsed
 */
val JsonPrimitive.commonLocalDateTime: LocalDateTime
  get() = LocalDateTime.parse(content, commonLocalDateTimeFormatter)

/**
 * Returns content of the current element as ISO format [LocalDateTime]
 * @throws DateTimeParseException if the content cannot be parsed
 */
val JsonPrimitive.commonLocalDateTimeOrNull: LocalDateTime?
  get() = if (this is JsonNull || content.isEmpty()) null
  else LocalDateTime.parse(content, commonLocalDateTimeFormatter)

val TO_LONG_MAPPER: (value: JsonElement) -> Long? = { value -> value.jsonPrimitive.longOrNull }
val TO_INT_MAPPER: (value: JsonElement) -> Int? = { value -> value.jsonPrimitive.intOrNull }
val TO_SHORT_MAPPER: (value: JsonElement) -> Short? = { value -> value.jsonPrimitive.shortOrNull }
val TO_BIG_DECIMAL_MAPPER: (value: JsonElement) -> BigDecimal? =
  { value -> value.jsonPrimitive.bigDecimalOrNull }
val TO_DOUBLE_MAPPER: (value: JsonElement) -> Double? = { value -> value.jsonPrimitive.doubleOrNull }
val TO_FLOAT_MAPPER: (value: JsonElement) -> Float? = { value -> value.jsonPrimitive.floatOrNull }
val TO_ISO_LOCAL_DATE_MAPPER: (value: JsonElement) -> LocalDate? =
  { value -> value.jsonPrimitive.isoLocalDateOrNull }
val TO_ISO_LOCAL_TIME_MAPPER: (value: JsonElement) -> LocalTime? =
  { value -> value.jsonPrimitive.isoLocalTimeOrNull }
val TO_ISO_LOCAL_DATE_TIME_MAPPER: (value: JsonElement) -> LocalDateTime? =
  { value -> value.jsonPrimitive.isoLocalDateTimeOrNull }

/**
 * Convert the [JsonObject] instance to a [Map] with native value.
 * Default [JsonPrimitive] value is auto convert to [String] type value.
 */
fun JsonObject.toNativeMap(
  key: String = "",
  defaultDecimalValueMapper: (value: JsonPrimitive) -> Number? = TO_BIG_DECIMAL_MAPPER,
  defaultIntegerValueMapper: (value: JsonPrimitive) -> Number? = TO_LONG_MAPPER,
  valueMapper: Map<String, (value: JsonElement) -> Any?> = emptyMap(),
): Map<String, Any?> {
  return this.entries.associate {
    it.key to it.value.toNativeValue(
      key = if (key.isEmpty()) it.key else "${key}.${it.key}",
      valueMapper = valueMapper,
      defaultIntegerValueMapper = defaultIntegerValueMapper,
      defaultDecimalValueMapper = defaultDecimalValueMapper,
    )
  }
}

/**
 * Convert the [JsonArray] instance to a [List] with native [Map] value.
 * Default [JsonPrimitive] value is auto convert to [String] type value.
 */
fun <T> JsonArray.toNativeList(
  key: String = "",
  defaultDecimalValueMapper: (value: JsonPrimitive) -> Number? = TO_BIG_DECIMAL_MAPPER,
  defaultIntegerValueMapper: (value: JsonPrimitive) -> Number? = TO_LONG_MAPPER,
  valueMapper: Map<String, (value: JsonElement) -> Any?> = emptyMap(),
): List<T> {
  return this.map {
    @Suppress("UNCHECKED_CAST")
    it.toNativeValue(
      key = key,
      valueMapper = valueMapper,
      defaultIntegerValueMapper = defaultIntegerValueMapper,
      defaultDecimalValueMapper = defaultDecimalValueMapper,
    ) as T
  }
}

/**
 * Convert the [JsonElement] instance to a native value.
 */
fun JsonElement.toNativeValue(
  key: String,
  defaultDecimalValueMapper: (value: JsonPrimitive) -> Number? = TO_BIG_DECIMAL_MAPPER,
  defaultIntegerValueMapper: (value: JsonPrimitive) -> Number? = TO_LONG_MAPPER,
  valueMapper: Map<String, (value: JsonElement) -> Any?> = emptyMap(),
): Any? {
  return if (valueMapper.containsKey(key)) valueMapper[key]!!.invoke(this)
  else {
    when (this) {
      is JsonNull -> null
      is JsonPrimitive -> {
        // confirm string first
        if (this.isString) return this.content

        // try number and boolean
        this.longOrNull?.let { return defaultIntegerValueMapper(this) }
        this.doubleOrNull?.let { return defaultDecimalValueMapper(this) }
        this.booleanOrNull?.let { return it }

        // default string
        this.content
      }

      is JsonArray -> this.map {
        it.toNativeValue(
          key = key,
          valueMapper = valueMapper,
          defaultDecimalValueMapper = defaultDecimalValueMapper,
          defaultIntegerValueMapper = defaultIntegerValueMapper,
        )
      }

      is JsonObject -> this.toNativeMap(
        key = key,
        valueMapper = valueMapper,
        defaultDecimalValueMapper = defaultDecimalValueMapper,
        defaultIntegerValueMapper = defaultIntegerValueMapper,
      )
    }
  }
}

/**
 * Convert the native value [Map] instance to a [JsonElement] instance.
 */
fun fromNativeMap(
  source: Map<String, Any?>,
  valueMapper: Map<String, (value: Any?) -> JsonElement> = emptyMap(),
  key: String = "",
): JsonObject {
  return buildJsonObject {
    source.forEach { (k, v) ->
      val combineKey = if (key.isEmpty()) k else "${key}.${k}"
      put(
        key = k,
        element = fromNativeValue(
          key = combineKey,
          value = v,
          valueMapper = valueMapper,
        )
      );
    }
  }
}

/**
 * Convert the native value [Map] instance to a [JsonElement] instance.
 */
fun fromNativeCollection(
  source: Collection<Any?>,
  valueMapper: Map<String, (value: Any?) -> JsonElement> = emptyMap(),
  key: String = "",
): JsonArray {
  return buildJsonArray {
    source.forEach { v ->
      add(
        fromNativeValue(
          key = key,
          value = v,
          valueMapper = valueMapper,
        )
      )
    }
  }
}

/**
 * Convert the native value to a [JsonElement] instance.
 */
fun fromNativeValue(
  value: Any?,
  valueMapper: Map<String, (value: Any?) -> JsonElement> = emptyMap(),
  key: String = "",
): JsonElement {
  @Suppress("UNCHECKED_CAST")
  return if (valueMapper.containsKey(key)) valueMapper[key]!!.invoke(value)
  else when (value) {
    null -> JsonNull
    is Boolean -> JsonPrimitive(value)
    is String -> JsonPrimitive(value)
    is Number -> JsonPrimitive(value)
    is Map<*, *> -> fromNativeMap(
      key = key,
      source = value as Map<String, Any?>,
      valueMapper = valueMapper,
    )

    is Collection<*> -> fromNativeCollection(
      key = key,
      source = value,
      valueMapper = valueMapper,
    )

    else -> JsonPrimitive(value.toString())
  }
}
