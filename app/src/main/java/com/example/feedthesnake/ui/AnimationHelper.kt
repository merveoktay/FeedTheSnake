package com.example.feedthesnake.ui

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import com.example.feedthesnake.constants.SizeConstants
import com.example.feedthesnake.theme.Blue
import com.example.feedthesnake.theme.LightBlue


import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color



object AnimationHelper {

    @Composable
    fun infiniteTransitionFloat() = rememberInfiniteTransition()

    @Composable
    fun infiniteFloatTransition(infiniteTransition: InfiniteTransition): State<Float> {
        return infiniteTransition.animateFloat(
            initialValue = SizeConstants.MIN_ANIMATED_OFFSET,
            targetValue = SizeConstants.MAX_ANIMATED_OFFSET,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = SizeConstants.DURATION_MILLIS, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    @Composable
    fun firstAnimatedColor(infiniteTransition: InfiniteTransition): State<Color> {
        val animatedColor1 = infiniteTransition.animateColor(
            initialValue = LightBlue,
            targetValue = Blue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = SizeConstants.DURATION_MILLIS,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )


        return animatedColor1
    }
    @Composable
    fun secondAnimatedColor(infiniteTransition: InfiniteTransition): State<Color> {
        val animatedColor2 = infiniteTransition.animateColor(
            initialValue = Blue,
            targetValue = LightBlue,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = SizeConstants.DURATION_MILLIS, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        return animatedColor2
    }
}
