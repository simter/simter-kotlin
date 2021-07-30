package tech.simter.kotlin.data

import kotlinx.serialization.Serializable

interface Id<T> {
  val id: T

  companion object {
    fun of(id: Int): IntId = IntId(id)
    fun of(id: Long): LongId = LongId(id)
    fun of(id: String): StringId = StringId(id)
  }

  @Serializable
  data class IntId(override val id: Int) : Id<Int>

  @Serializable
  data class LongId(override val id: Long) : Id<Long>

  @Serializable
  data class StringId(override val id: String) : Id<String>
}