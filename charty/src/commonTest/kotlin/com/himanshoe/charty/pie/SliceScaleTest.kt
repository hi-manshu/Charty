package com.himanshoe.charty.pie

import com.himanshoe.charty.pie.internal.sliceScale
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SliceScaleTest {
    @Test
    fun `an idle slice draws at its natural size`() {
        assertEquals(expected = 1f, actual = sliceScale(isSelected = false, selectedScale = 1.1f, isHovered = false))
    }

    @Test
    fun `a hovered slice grows, but only a little`() {
        val scale = sliceScale(isSelected = false, selectedScale = 1.1f, isHovered = true)

        assertTrue(actual = scale > 1f, message = "hover should be visible")
        assertTrue(actual = scale < 1.1f, message = "hover should stay smaller than selection")
    }

    @Test
    fun `selection wins over hover, so a chosen slice never shrinks under the pointer`() {
        assertEquals(expected = 1.1f, actual = sliceScale(isSelected = true, selectedScale = 1.1f, isHovered = true))
    }
}
