package com.himanshoe.charty

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.ui.theme.ChartylibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChartylibraryTheme {
                BarChart(
                    modifier = Modifier
                        .size(width = 500.dp, height = 300.dp)
                        .padding(20.dp),
                    onBarClick = {
                    },
                    color = Color.Green,
                    barData = listOf(
                        BarData(10F, 5F),
                        BarData(20F, 25F),
                        BarData(10F, 5F),
                        BarData(100F, 10F),
                        BarData(10F, 15F),
                        BarData(50F, 20F),
                        BarData(20F, 25F),
                    )
                )
            }
        }
    }
}

