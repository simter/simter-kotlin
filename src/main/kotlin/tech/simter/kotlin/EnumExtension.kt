package tech.simter.kotlin

/** Find the first enum matching the given parameter value, or null if enum was not found. */
inline fun <reified T : Enum<T>, V> ((T) -> V).firstOrNull(value: V): T? {
  return enumValues<T>().firstOrNull { this(it) == value }
}

/**
 * Find the first enum matching the given parameter value.
 *
 * @return the first enum matching the given parameter value.
 * @throws [NoSuchElementException] if no such enum is found.
 */
inline fun <reified T : Enum<T>, V> ((T) -> V).first(value: V): T {
  return enumValues<T>().first { this(it) == value }
}
