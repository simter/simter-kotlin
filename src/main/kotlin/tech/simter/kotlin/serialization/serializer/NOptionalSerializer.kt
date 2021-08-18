package tech.simter.kotlin.serialization.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import tech.simter.kotlin.beans.NOptional

class NOptionalSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<NOptional<T>> {
  override val descriptor: SerialDescriptor = dataSerializer.descriptor

  @ExperimentalSerializationApi
  override fun serialize(encoder: Encoder, value: NOptional<T>) {
    value.ifPresent {
      if (it != null) dataSerializer.serialize(encoder, it)
      else encoder.encodeNull()
    }
  }

  override fun deserialize(decoder: Decoder): NOptional<T> {
    return NOptional.of(dataSerializer.deserialize(decoder))
  }
}