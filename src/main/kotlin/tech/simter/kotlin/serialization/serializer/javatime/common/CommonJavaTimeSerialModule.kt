package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.modules.SerializersModule
import tech.simter.kotlin.serialization.serializer.javatime.int.IntMonthSerializer
import tech.simter.kotlin.serialization.serializer.javatime.int.IntYearSerializer
import tech.simter.kotlin.serialization.serializer.javatime.iso.IsoLocalDateSerializer
import tech.simter.kotlin.serialization.serializer.javatime.iso.IsoLocalTimeSerializer
import tech.simter.kotlin.serialization.serializer.javatime.iso.IsoMonthDaySerializer
import tech.simter.kotlin.serialization.serializer.javatime.iso.IsoYearMonthSerializer
import java.time.*

/**
 * All common format javatime serializers.
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
 * private val json = Json { serializersModule = CommonJavaTimeSerialModule }
 * val bean = json.decodeFromString<Bean>("""{"p": "2019-12-01 10:20:30"}""")
 * val str = json.encodeToString(Bean(LocalDateTime.of(2019, 12, 1, 10, 20, 30)))
 * ```
 */
val CommonJavaTimeSerialModule: SerializersModule = SerializersModule {
  contextual(OffsetDateTime::class, CommonOffsetDateTimeSerializer)
  contextual(LocalDateTime::class, CommonLocalDateTimeSerializer)
  contextual(LocalDate::class, IsoLocalDateSerializer)
  contextual(LocalTime::class, IsoLocalTimeSerializer)
  contextual(MonthDay::class, IsoMonthDaySerializer)
  contextual(Month::class, IntMonthSerializer)
  contextual(YearMonth::class, IsoYearMonthSerializer)
  contextual(Year::class, IntYearSerializer)
}