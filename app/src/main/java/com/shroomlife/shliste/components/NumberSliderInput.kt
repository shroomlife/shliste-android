package com.shroomlife.shliste.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.ui.theme.PrimaryColor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun NumberSliderInput(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    val MAX_VALUE = 999
    val animatedOffset = remember { Animatable(0f) }
    val haptics = LocalHapticFeedback.current
    val currentValue by rememberUpdatedState(value)

    Box(
        modifier = modifier
            .size(height = 60.dp, width = 50.dp)
            .background(Color.White)
            .pointerInput(Unit) {
                coroutineScope {
                    // Kombinierte Gestenerkennung
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            // Hier könnten wir auf PointerEventType.Press reagieren
                        }
                    }
                }
            }
            .pointerInput(Unit) {
                coroutineScope {
                    detectVerticalDragGestures(
                        onVerticalDrag = { _, dragAmount ->
                            launch {
                                animatedOffset.snapTo(animatedOffset.value + dragAmount / 2)

                                when {
                                    animatedOffset.value < -40 -> {
                                        val newValue = (currentValue + 1).coerceAtMost(MAX_VALUE)
                                        if (newValue != currentValue) {
                                            onValueChange(newValue)
                                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        }
                                        animatedOffset.snapTo(0f)
                                    }

                                    animatedOffset.value > 40 -> {
                                        val newValue = (currentValue - 1).coerceAtLeast(1)
                                        if (newValue != currentValue) {
                                            onValueChange(newValue)
                                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        }
                                        animatedOffset.snapTo(0f)
                                    }
                                }
                            }
                        },
                        onDragEnd = {
                            launch {
                                animatedOffset.animateTo(0f, animationSpec = tween(200))
                            }
                        }
                    )
                }
            }
            // Zweiter Input-Block nur für Tap (sauber getrennt vom Drag)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        val newValue = (currentValue + 1).coerceAtMost(MAX_VALUE)
                        if (newValue != currentValue) {
                            onValueChange(newValue)
                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${value}x",
            color = PrimaryColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset { IntOffset(0, animatedOffset.value.roundToInt()) }
        )
    }
}