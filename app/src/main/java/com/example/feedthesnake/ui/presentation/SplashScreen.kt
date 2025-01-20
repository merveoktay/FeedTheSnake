package com.example.feedthesnake.ui.presentation


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants
import com.example.feedthesnake.ui.AnimationHelper


@Composable
fun SplashScreen(onNavigateToSecondSplash: () -> Unit) {
    val infiniteTransition = AnimationHelper.infiniteTransitionFloat()

    val animatedOffsetX = AnimationHelper.infiniteFloatTransition(infiniteTransition)

    val animatedColor1 = AnimationHelper.firstAnimatedColor(infiniteTransition)

    val animatedColor2 = AnimationHelper.secondAnimatedColor(infiniteTransition)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(animatedColor1.value, animatedColor2.value),
                    start = Offset(animatedOffsetX.value, SizeConstants.MIN_ANIMATED_OFFSET),
                    end = Offset(animatedOffsetX.value + SizeConstants.MAX_ANIMATED_OFFSET, SizeConstants.MAX_ANIMATED_OFFSET)
                )
            )
            .clickable { onNavigateToSecondSplash() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_icon),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(SizeConstants.IMAGE_MAX_SIZE),
                contentScale = ContentScale.Fit
            )
        }
    }
}
