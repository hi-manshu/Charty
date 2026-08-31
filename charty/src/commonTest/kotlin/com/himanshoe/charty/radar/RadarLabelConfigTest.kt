package com.himanshoe.charty.radar

import com.himanshoe.charty.radar.config.RadarLabelConfig
import com.himanshoe.charty.radar.config.RadarValuePlacement
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RadarLabelConfigTest {
    @Test
    fun `values default to the data points, where they have always drawn`() {
        assertEquals(expected = RadarValuePlacement.DATA_POINT, actual = RadarLabelConfig().valuePlacement)
    }

    @Test
    fun `a negative gap is rejected at construction`() {
        assertFailsWith<IllegalArgumentException> { RadarLabelConfig(valueGapFraction = -0.1f) }
    }

    @Test
    fun `a zero gap is a legitimate choice, not an error`() {
        assertEquals(expected = 0f, actual = RadarLabelConfig(valueGapFraction = 0f).valueGapFraction)
    }

    @Test
    fun `positional calls written against 3-1-1 still compile and mean the same thing`() {
        // The new parameters were nearly inserted mid-list, which would have broken exactly this
        // call. They sit last instead; this test is the tripwire for the next such insertion.
        val config = RadarLabelConfig(true, false, 1.3f)

        assertEquals(expected = true, actual = config.showLabels)
        assertEquals(expected = false, actual = config.showValues)
        assertEquals(expected = 1.3f, actual = config.labelDistanceMultiplier)
        assertEquals(expected = RadarValuePlacement.DATA_POINT, actual = config.valuePlacement)
    }
}
