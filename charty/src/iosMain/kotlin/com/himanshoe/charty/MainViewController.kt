package com.himanshoe.charty

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.himanshoe.charty.stockageBar.StorageBar
import com.himanshoe.charty.stockageBar.model.StorageData

fun MainViewController() = ComposeUIViewController(configure = { enforceStrictPlistSanityCheck = false }) {

StorageBar(
        data = generateMockStorageCategories(),
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(12.dp)
            .fillMaxWidth()
            .height(30.dp)
    )
}
private fun generateMockStorageCategories(): List<StorageData> {
    return listOf(
        StorageData(name = "System", value = 0.05f, color = Color.Gray),
        StorageData(name = "Apps", value = 0.20f, color = Color.Red),
        StorageData(name = "Garima", value = 0.10f, color = Color.Magenta),
        StorageData(name = "Himanshu", value = 0.10f, color = Color.Blue),
    )
}