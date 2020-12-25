package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.modules.SerializersModule
import tech.simter.kotlin.serialization.serializer.javatime.int.IntMonthSerializer
import tech.simter.kotlin.serialization.serializer.javatime.int.IntYearSerializer
import java.time.*

/**
 * All ISO format javatime serializers.
 *
 * Usage case: (use `@Contextual` on a property)
 *
 * ```
 * @Serializable
 * data class Bean(
 *   @Contextual
 *   val p: LocalDateTime
 * )
 *
 * private val json = Json { serializersModule = IsoJavaTimeSerialModule }
 * val bean = json.decodeFromString<Bean>("""{"p": "2019-12-01T10:20:30"}""")
 * val str = json.encodeToString(Bean(LocalDateTime.of(2019, 12, 1, 10, 20, 30)))
 * ```
 */
val IsoJavaTimeSerialModule: SerializersModule = SerializersModule {
  contextual(OffsetDateTime::class, IsoOffsetDateTimeSerializer)
  contextual(LocalDateTime::class, IsoLocalDateTimeSerializer)
  contextual(LocalDate::class, IsoLocalDateSerializer)
  contextual(LocalTime::class, IsoLocalTimeSerializer)
  contextual(MonthDay::class, IsoMonthDaySerializer)
  contextual(Month::class, IntMonthSerializer)
  contextual(YearMonth::class, IsoYearMonthSerializer)
  contextual(Year::class, IntYearSerializer)
}