package com.example.feedthesnake.ui.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants
import com.example.feedthesnake.theme.LightBlue

@Composable
fun SecondSplashScreen(onNavigateToHome: () -> Unit) {

    val alpha = remember { Animatable(0f) }

    val infiniteTransition = AnimationHelper.infiniteTransitionFloat()

    val animatedOffsetX = AnimationHelper.infiniteFloatTransition(infiniteTransition)

    val animatedColor1 = AnimationHelper.firstAnimatedColor(infiniteTransition)

    val animatedColor2 = AnimationHelper.secondAnimatedColor(infiniteTransition)

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background( Brush.linearGradient(
                colors = listOf(animatedColor1.value, animatedColor2.value),
                start = Offset(animatedOffsetX.value, SizeConstants.MIN_ANIMATED_OFFSET),
                end = Offset(animatedOffsetX.value + SizeConstants.MAX_ANIMATED_OFFSET, SizeConstants.MAX_ANIMATED_OFFSET)
            ))
            .clickable { onNavigateToHome() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image with animated alpha value
            Image(
                painter = painterResource(id = R.drawable.welcome),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
                    .size(SizeConstants.IMAGE_MAX_SIZE)
                    .graphicsLayer(alpha = alpha.value), // Apply the animated alpha value
                contentScale = ContentScale.Fit
            )
        }
    }
}
