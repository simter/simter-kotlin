package tech.simter.kotlin.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.simter.kotlin.data.Page.Companion.calculateOffset
import tech.simter.kotlin.data.Page.Companion.calculatePageCount
import tech.simter.kotlin.data.Page.Companion.calculatePageNo

/**
 * Test [Page].
 *
 * @author RJ
 */
class PageTest {
  @Test
  fun testCalculatePageNo() {
    assertThat(calculatePageNo(0, 0)).isEqualTo(1)
    assertThat(calculatePageNo(0, -1)).isEqualTo(1)
    assertThat(calculatePageNo(100, 0)).isEqualTo(1)
    assertThat(calculatePageNo(100, -1)).isEqualTo(1)

    assertThat(calculatePageNo(0, 25)).isEqualTo(1)
    assertThat(calculatePageNo(24, 25)).isEqualTo(1)
    assertThat(calculatePageNo(25, 25)).isEqualTo(2)
    assertThat(calculatePageNo(26, 25)).isEqualTo(2)
  }

  @Test
  fun testCalculateOffset() {
    assertThat(calculateOffset(0, 0)).isEqualTo(0)
    assertThat(calculateOffset(1, 0)).isEqualTo(0)
    assertThat(calculateOffset(-1, 0)).isEqualTo(0)
    assertThat(calculateOffset(-10, 0)).isEqualTo(0)

    assertThat(calculateOffset(1, 25)).isEqualTo(0)
    assertThat(calculateOffset(2, 25)).isEqualTo(25)
    assertThat(calculateOffset(3, 25)).isEqualTo(50)
  }

  @Test
  fun testCalculatePageCount() {
    assertThat(calculatePageCount(0, 0)).isEqualTo(0)
    assertThat(calculatePageCount(1, 0)).isEqualTo(0)
    assertThat(calculatePageCount(-1, 0)).isEqualTo(0)

    assertThat(calculatePageCount(0, 1)).isEqualTo(0)
    assertThat(calculatePageCount(1, 1)).isEqualTo(1)
    assertThat(calculatePageCount(2, 1)).isEqualTo(2)

    assertThat(calculatePageCount(0, 25)).isEqualTo(0)
    assertThat(calculatePageCount(1, 25)).isEqualTo(1)
    assertThat(calculatePageCount(24, 25)).isEqualTo(1)
    assertThat(calculatePageCount(25, 25)).isEqualTo(1)
    assertThat(calculatePageCount(26, 25)).isEqualTo(2)
  }
}