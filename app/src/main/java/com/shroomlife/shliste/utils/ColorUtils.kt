package com.shroomlife.shliste.utils

import android.graphics.Color as AndroidColor
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import kotlin.random.Random

object ColorUtils {
    fun getRandomColor(): Int {
        var r: Int
        var g: Int
        var b: Int

        do {
            r = Random.nextInt(256)
            g = Random.nextInt(256)
            b = Random.nextInt(256)
        } while ((r + g + b) < 100 || (r + g + b) > 700)

        val alpha = (0.20f * 255).toInt()

        return AndroidColor.argb(alpha, r, g, b)
    }

    fun getColorFromString(input: String): Color {
        // Parse string to color int
        val baseColorInt = input.toColorInt()
        val baseColor = Color(baseColorInt)
        // Always apply 20% opacity
        return baseColor.copy(alpha = 0.2f)
    }

}
