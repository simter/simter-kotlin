package tech.simter.kotlin.serialization.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

class OptionalSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<Optional<T>> {
  override val descriptor: SerialDescriptor = dataSerializer.descriptor

  @ExperimentalSerializationApi
  override fun serialize(encoder: Encoder, value: Optional<T>) {
    println("-----$value")
    if (value.isPresent) dataSerializer.serialize(encoder, value.get())
    else encoder.encodeNull()
  }

  override fun deserialize(decoder: Decoder): Optional<T> {
    println("-----s0")
    val value = dataSerializer.deserialize(decoder)
    println("-----s")
    println(value)
    println("-----e")
    return Optional.ofNullable(value)
  }
}