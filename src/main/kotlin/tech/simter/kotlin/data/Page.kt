package tech.simter.kotlin.data

import kotlinx.serialization.Serializable

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

  private data class Impl<T>(
    override val offset: Int,
    override val limit: Int,
    override val total: Long,
    override val rows: List<T>
  ) : Page<T>

  /** Convert this [Page] to a [Map] structure with specific [propertyMapper] */
  fun toMap(vararg propertyMapper: Pair<String, String>): Map<String, Any> {
    return toMap(this, *propertyMapper)
  }

  companion object {
    private object EmptyPage : Page<Nothing> {
      override val offset: Int = 0
      override val limit: Int = 0
      override val total: Long = 0
      override val rows: List<Nothing> = emptyList()
    }

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

    /**
     * Returns an empty page.
     *
     * The returned page is kotlin [Serializable].
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> emptyPage(): Page<T> = EmptyPage as Page<T>

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

    private fun getMappedKey(keyMapper: Map<String, String>, key: String): String {
      return keyMapper.getOrDefault(key, key)
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
  }
}