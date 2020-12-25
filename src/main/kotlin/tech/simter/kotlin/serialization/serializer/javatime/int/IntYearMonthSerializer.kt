package tech.simter.kotlin.serialization.serializer.javatime.int

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * A [KSerializer] between [YearMonth] and 6 digit number with pattern `yyyyMM`.
 *
 * @author RJ
 */
object IntYearMonthSerializer : KSerializer<YearMonth> {
  private val formatter = DateTimeFormatter.ofPattern("yyyyMM")
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
    serialName = IntYearMonthSerializer::class.qualifiedName!!,
    kind = PrimitiveKind.INT
  )

  override fun deserialize(decoder: Decoder): YearMonth {
    return YearMonth.parse(decoder.decodeInt().toString(), formatter)
  }

  override fun serialize(encoder: Encoder, value: YearMonth) {
    encoder.encodeInt(value.format(formatter).toInt())
  }
}