package tech.simter.kotlin.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import tech.simter.kotlin.data.Page.Companion.MappedType.OffsetLimit
import tech.simter.kotlin.data.Page.Companion.MappedType.PageNoPageSize

/**
 * The page data holder.
 *
 * @author RJ
 */
interface Page<T> {
  /** The zero-base start point */
  val offset: Long

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

  companion object {
    const val DEFAULT_LIMIT: Int = 25

    /**
     * The default internal [Page] implementation.
     *
     * It is a kotlin [Serializable] [Page] type.
     */
    @Serializable
    @SerialName("Page")
    internal data class Impl<T>(
      override val offset: Long,
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
    fun <T> of(limit: Int, offset: Long = 0, total: Long = 0, rows: List<T> = emptyList()): Page<T> {
      return Impl(
        offset = offset,
        limit = limit,
        total = total,
        rows = rows
      )
    }

    /** Calculate 0-base start point */
    fun calculateOffset(pageNo: Int, limit: Int): Long {
      return 0L.coerceAtLeast((pageNo - 1) * limit.toLong())
    }

    /** Calculate 1-base page number */
    fun calculatePageNo(offset: Long, limit: Int): Int {
      return if (limit <= 0) 1 else 1.coerceAtLeast((offset / limit + 1).toInt())
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

    enum class MappedType {
      OffsetLimit, PageNoPageSize, Both
    }

    /** Convert [Page] to a [Map] structure with specific [mappedProperties] for [Json] serialization */
    inline fun <reified T> toMap(
      page: Page<T>,
      json: Json,
      mappedType: MappedType = MappedType.Both,
      mappedProperties: Map<String, String> = emptyMap()
    ): Map<String, JsonElement> {
      return when (mappedType) {
        OffsetLimit -> mapOf(
          getMappedKey(mappedProperties, "offset") to JsonPrimitive(page.offset),
          getMappedKey(mappedProperties, "limit") to JsonPrimitive(page.limit),
          getMappedKey(mappedProperties, "total") to JsonPrimitive(page.total),
          getMappedKey(mappedProperties, "pageCount") to JsonPrimitive(page.pageCount),
          getMappedKey(mappedProperties, "rows") to JsonArray(page.rows.map { json.encodeToJsonElement(it) })
        )
        PageNoPageSize -> mapOf(
          getMappedKey(mappedProperties, "pageNo") to JsonPrimitive(page.pageNo),
          getMappedKey(mappedProperties, "pageSize ") to JsonPrimitive(page.limit),
          getMappedKey(mappedProperties, "total") to JsonPrimitive(page.total),
          getMappedKey(mappedProperties, "pageCount") to JsonPrimitive(page.pageCount),
          getMappedKey(mappedProperties, "rows") to JsonArray(page.rows.map { json.encodeToJsonElement(it) })
        )
        else -> mapOf(
          getMappedKey(mappedProperties, "offset") to JsonPrimitive(page.offset),
          getMappedKey(mappedProperties, "limit") to JsonPrimitive(page.limit),
          getMappedKey(mappedProperties, "total") to JsonPrimitive(page.total),
          getMappedKey(mappedProperties, "pageNo") to JsonPrimitive(page.pageNo),
          getMappedKey(mappedProperties, "pageSize") to JsonPrimitive(page.limit),
          getMappedKey(mappedProperties, "count") to JsonPrimitive(page.total),
          getMappedKey(mappedProperties, "pageCount") to JsonPrimitive(page.pageCount),
          getMappedKey(mappedProperties, "rows") to JsonArray(page.rows.map { json.encodeToJsonElement(it) })
        )
      }
    }
  }
}