package tech.simter.kotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EnumExtensionTest {
  enum class MyEnum(val num: Short) {
    First(1),
    Second(2),
  }

  @Test
  fun enumValueOf() {
    assertThat(enumValueOf<MyEnum>("Second")).isEqualTo(MyEnum.Second)
    assertThrows(IllegalArgumentException::class.java) { enumValueOf<MyEnum>("NO_EXISTS") }
  }

  @Test
  fun firstOrNull() {
    assertEquals(MyEnum.Second, MyEnum::num.firstOrNull(2))
    assertNull(MyEnum::num.firstOrNull(3))
  }

  @Test
  fun first() {
    assertEquals(MyEnum.Second, MyEnum::num.first(2))
    assertThrows(NoSuchElementException::class.java) { MyEnum::num.first(3) }
  }
}