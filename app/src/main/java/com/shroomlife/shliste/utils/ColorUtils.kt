package com.shroomlife.shliste.utils

import android.graphics.Color as AndroidColor
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import kotlin.random.Random

object ColorUtils {
    fun getRandomColor(): String {
        var r: Int
        var g: Int
        var b: Int

        do {
            r = Random.nextInt(256)
            g = Random.nextInt(256)
            b = Random.nextInt(256)
        } while (r + g + b !in 100..700)

        return String.format("#%02X%02X%02X", r, g, b)
    }
}
