package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerialModule
import kotlinx.serialization.modules.serializersModuleOf
import tech.simter.kotlin.serialization.serializer.javatime.int.IntMonthSerializer
import tech.simter.kotlin.serialization.serializer.javatime.int.IntYearSerializer
import java.time.*
import kotlin.reflect.KClass

/**
 * All ISO format javatime serializers.
 *
 * ## Usage case 1: use `@ContextualSerialization` on a property
 *
 * ```
 * @Serializable
 * data class Bean(
 *   @ContextualSerialization
 *   val p: LocalDate
 * )
 *
 * private val json = Json(context = IsoJavaTimeSerialModule)
 * val obj: LocalDateTime = json.parse(Bean.serializer(), """{"p": "2019-12-01T10:20:30"}""")
 * val str: String = json.stringify(Bean.serializer(), LocalDateTime.of(2019, 12, 1, 10, 20, 30))
 * ```
 *
 *
 * ## Usage case 2: use `@file:ContextualSerialization(...)` on a file
 *
 * ```
 * @file:ContextualSerialization(LocalDate::class, ...)
 * package ...
 * import ...
 * class XTest {
 *   private val json = Json(configuration = Stable, context = IsoJavaTimeSerialModule)
 *
 *   @Serializable
 *   data class Bean(
 *     val p: LocalDate,
 *     ...
 *   )
 *
 *   @Test
 *   fun test() {
 *     val obj: LocalDateTime = json.parse(Bean.serializer(), """{"p": "2019-12-01T10:20:30"}""")
 *     val str: String = json.stringify(Bean.serializer(), LocalDateTime.of(2019, 12, 1, 10, 20, 30))
 *   }
 * }
 * ```
 */
val IsoJavaTimeSerialModule: SerialModule = serializersModuleOf(mapOf<KClass<*>, KSerializer<*>>(
  OffsetDateTime::class to IsoOffsetDateTimeSerializer,
  LocalDateTime::class to IsoLocalDateTimeSerializer,
  LocalDate::class to IsoLocalDateSerializer,
  LocalTime::class to IsoLocalTimeSerializer,
  MonthDay::class to IsoMonthDaySerializer,
  Month::class to IntMonthSerializer,
  YearMonth::class to IsoYearMonthSerializer,
  Year::class to IntYearSerializer
))