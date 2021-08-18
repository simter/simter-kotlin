package tech.simter.kotlin.beans

import java.util.function.Consumer

data class NOptional<T> internal constructor(val value: T? = null, val empty: Boolean = true) {
  fun ifPresent1(consumer: Consumer<T?>) {
    if (!empty) consumer.accept(value)
  }

  fun ifPresent(consumer: (value: T?) -> Unit) {
    if (!empty) consumer.invoke(value)
  }

  companion object {
    private val EMPTY: NOptional<*> = NOptional<Any>(empty = true)

    @JvmStatic
    fun <T> empty(): NOptional<T> {
      @Suppress("UNCHECKED_CAST")
      return EMPTY as NOptional<T>
    }

    @JvmStatic
    fun <T> of(value: T?): NOptional<T> {
      return NOptional(value = value, empty = false)
    }
  }
}