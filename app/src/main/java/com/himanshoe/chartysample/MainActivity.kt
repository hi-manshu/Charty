package com.himanshoe.chartysample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.line.CurveLineChart
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.point.PointChart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContent {
                ChartContent(Modifier.fillMaxSize())
            }
        }
    }

    @Composable
    private fun ChartContent(modifier: Modifier = Modifier) {
        LazyColumn(modifier) {
            item {
                CurveLineChart(
                    dataCollection = ChartDataCollection(generateMockLineDataList()),
                    modifier = Modifier
                        .size(450.dp),
                )
            }
            item {
                LineChart(
                    dataCollection = ChartDataCollection(generateMockLineDataList()),
                    modifier = Modifier
                        .size(450.dp),
                )
            }
            item {
                PointChart(
                    dataCollection = ChartDataCollection(generateMockLineDataList()),
                    modifier = Modifier
                        .size(450.dp),
                )
            }
        }
    }

    private fun generateMockLineDataList(): List<LineData> {
        return listOf(
            LineData(0F, "Jan"),
            LineData(10F, "Feb"),
            LineData(05F, "Mar"),
            LineData(50F, "Apr"),
            LineData(55F, "May"),
            LineData(03F, "June"),
            LineData(9F, "July"),
            LineData(40F, "Aug"),
            LineData(60F, "Sept"),
            LineData(33F, "Oct"),
            LineData(11F, "Nov"),
        )
    }
}
