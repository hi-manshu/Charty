package com.himanshoe.charty.bar.model

import androidx.compose.ui.graphics.Color

/**
 * Data class representing storage data.
 *
 * @property name The name of the storage item.
 * @property value The value associated with the storage item.
 * @property color The color representing the storage item.
 */
data class StorageData(
    val name: String,
    val value: Float,
    val color: Color
)