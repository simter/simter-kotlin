package tech.simter.kotlin.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @author RJ
 */
class KSerializerInheritanceTest {
  private val stableJson = Json {}

  @Serializable
  private data class Bean(
    @Serializable(with = IsoLocalDateSerializer::class)
    val p: LocalDate
  )

  @Test
  fun test() {
    val json = """{"p":"2019-12-01"}"""
    val bean = Bean(p = LocalDate.of(2019, 12, 1))

    // deserialize
    assertThat(stableJson.decodeFromString<Bean>(json)).isEqualTo(bean)

    // serialize
    assertThat(stableJson.encodeToString(bean)).isEqualTo(json)
  }
}

//@Serializer(forClass = LocalDate::class)
// - add this annotation would raise error "kotlinx.serialization.json.JsonDecodingException: Invalid JSON at 13: Expected '{, kind: CLASS'"
//   See https://github.com/Kotlin/kotlinx.serialization/issues/619
object IsoLocalDateSerializer : LocalDateSerializer(DateTimeFormatter.ISO_DATE)

open class LocalDateSerializer(private val formatter: DateTimeFormatter) : KSerializer<LocalDate> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
    serialName = "java.time.LocalDate",
    kind = PrimitiveKind.STRING
  )

  override fun deserialize(decoder: Decoder): LocalDate {
    return LocalDate.parse(decoder.decodeString(), formatter)
  }

  override fun serialize(encoder: Encoder, value: LocalDate) {
    encoder.encodeString(value.format(formatter))
  }
}