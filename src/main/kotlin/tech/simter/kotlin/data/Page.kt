package tech.simter.kotlin.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

/**
 * The page data holder.
 *
 * @author RJ
 */
interface Page<T> {
  /** The zero-base start point */
  val offset: Int

  /** The maximum size of each page */
  val limit: Int

  /** The total rows count match the query */
  val total: Long

  /** The current page data */
  val rows: List<T>

  /** The 1-base page number */
  val pageNo: Int
    get() = calculatePageNo(offset, limit)

  /** The count of total pages */
  val pageCount: Int
    get() = calculatePageCount(total, limit)

  /** Convert this [Page] to a [Map] structure with specific [propertyMapper] */
  fun toMap(vararg propertyMapper: Pair<String, String>): Map<String, Any> {
    return toMap(this, *propertyMapper)
  }

  companion object {
    /**
     * The default internal [Page] implementation.
     *
     * It is a kotlin [Serializable] [Page] type.
     */
    @Serializable
    @SerialName("Page")
    internal data class Impl<T>(
      override val offset: Int,
      override val limit: Int,
      override val total: Long,
      override val rows: List<T>
    ) : Page<T>

    /**
     * Returns an const empty page.
     *
     * The returned page is kotlin [Serializable].
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> empty(): Page<T> = EMPTY_PAGE as Page<T>
    private val EMPTY_PAGE = Impl<Nothing>(
      offset = 0,
      limit = 0,
      total = 0,
      rows = emptyList()
    )

    /**
     * Build a page instance.
     *
     * The returned page is kotlin [Serializable].
     */
    fun <T> of(limit: Int, offset: Int = 0, total: Long = 0, rows: List<T> = emptyList()): Page<T> {
      return Impl(
        offset = offset,
        limit = limit,
        total = total,
        rows = rows
      )
    }

    /** Calculate 0-base start point */
    fun calculateOffset(pageNo: Int, limit: Int): Int {
      return 0.coerceAtLeast((pageNo - 1) * limit)
    }

    /** Calculate 1-base page number */
    fun calculatePageNo(offset: Int, limit: Int): Int {
      return if (limit <= 0) 1 else 1.coerceAtLeast(offset / limit + 1)
    }

    /** Calculate the count of total pages */
    fun calculatePageCount(total: Long, limit: Int): Int {
      return if (limit <= 0) 0 else 0L.coerceAtLeast((total + limit - 1) / limit).toInt()
    }

    /**
     * Get a mapped key.
     *
     * If without a mapped key inside [keyMapper], use the [originalKey] instead.
     */
    fun getMappedKey(keyMapper: Map<String, String>, originalKey: String): String {
      return keyMapper.getOrDefault(originalKey, originalKey)
    }

    /** Convert [Page] to a [Map] structure with specific [propertyMapper] */
    fun <T> toMap(page: Page<T>, vararg propertyMapper: Pair<String, String>): Map<String, Any> {
      val map = mapOf(*propertyMapper)
      return mapOf(
        getMappedKey(map, "offset") to page.offset,
        getMappedKey(map, "limit") to page.limit,
        getMappedKey(map, "total") to page.total,
        getMappedKey(map, "pageNo") to page.pageNo,
        getMappedKey(map, "pageCount") to page.pageCount,
        getMappedKey(map, "rows") to page.rows
      )
    }

    /** Convert [Page] to a [Map] structure with specific [propertyMapper] for [Json] serialization */
    inline fun <reified T> toMap(page: Page<T>, json: Json, vararg propertyMapper: Pair<String, String>): Map<String, JsonElement> {
      val map = mapOf(*propertyMapper)
      return mapOf(
        getMappedKey(map, "offset") to JsonPrimitive(page.offset),
        getMappedKey(map, "limit") to JsonPrimitive(page.limit),
        getMappedKey(map, "total") to JsonPrimitive(page.total),
        getMappedKey(map, "pageNo") to JsonPrimitive(page.pageNo),
        getMappedKey(map, "pageCount") to JsonPrimitive(page.pageCount),
        getMappedKey(map, "rows") to JsonArray(page.rows.map { json.encodeToJsonElement(it) })
      )
    }
  }
}