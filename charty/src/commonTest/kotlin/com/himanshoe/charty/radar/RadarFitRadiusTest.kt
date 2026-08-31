package com.himanshoe.charty.radar

import com.himanshoe.charty.radar.internal.RadarFitBox
import com.himanshoe.charty.radar.internal.radarFitRadius
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val TOLERANCE = 0.01f

class RadarFitRadiusTest {
    @Test
    fun `a chart whose labels already fit keeps its padded radius exactly`() {
        val fit =
            radarFitRadius(
                centerX = 200f,
                centerY = 200f,
                canvasWidth = 400f,
                canvasHeight = 400f,
                labelDistanceMultiplier = 1.15f,
                boxes =
                    listOf(
                        RadarFitBox(angleRadians = 0f, offsetX = -10f, offsetY = -5f, width = 20f, height = 10f),
                    ),
                paddedRadius = 100f,
            )

        assertEquals(expected = 100f, actual = fit, absoluteTolerance = TOLERANCE)
    }

    @Test
    fun `a label that would leave the canvas shrinks the radius just enough`() {
        // Rightward axis, canvas 400 wide: the box's right edge is centre + r*1.0 + 40, so at the
        // padded 190 it would reach 430. Fitting solves r for edge == 400, which is 160.
        val fit =
            radarFitRadius(
                centerX = 200f,
                centerY = 200f,
                canvasWidth = 400f,
                canvasHeight = 400f,
                labelDistanceMultiplier = 1f,
                boxes =
                    listOf(
                        RadarFitBox(angleRadians = 0f, offsetX = -40f, offsetY = -5f, width = 80f, height = 10f),
                    ),
                paddedRadius = 190f,
            )

        assertEquals(expected = 160f, actual = fit, absoluteTolerance = TOLERANCE)
    }

    @Test
    fun `a downward axis constrains against the bottom edge, stack height included`() {
        // Downward axis (PI/2): the box top sits at centre + r - 8 and the box is 30 tall (label
        // plus stack), so the bottom edge reaches the 400px canvas when r = 178 - below the padded
        // 190, so the chart shrinks by exactly the 12px the stack needed.
        val fit =
            radarFitRadius(
                centerX = 200f,
                centerY = 200f,
                canvasWidth = 400f,
                canvasHeight = 400f,
                labelDistanceMultiplier = 1f,
                boxes =
                    listOf(
                        RadarFitBox(
                            angleRadians = (PI / 2).toFloat(),
                            offsetX = -20f,
                            offsetY = -8f,
                            width = 40f,
                            height = 30f,
                        ),
                    ),
                paddedRadius = 190f,
            )
        assertEquals(expected = 178f, actual = fit, absoluteTolerance = TOLERANCE)
    }

    @Test
    fun `a pathological label cannot shrink the chart past the floor`() {
        val fit =
            radarFitRadius(
                centerX = 200f,
                centerY = 200f,
                canvasWidth = 400f,
                canvasHeight = 400f,
                labelDistanceMultiplier = 1f,
                boxes =
                    listOf(
                        RadarFitBox(angleRadians = 0f, offsetX = -190f, offsetY = -5f, width = 380f, height = 10f),
                    ),
                paddedRadius = 190f,
            )

        assertTrue(actual = fit >= 190f * 0.4f - TOLERANCE, message = "the floor holds at 40% of padded")
    }
}
