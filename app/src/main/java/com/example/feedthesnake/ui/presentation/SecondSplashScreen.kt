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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.R
import com.example.feedthesnake.ui.theme.DarkGreen
import com.example.feedthesnake.ui.theme.LightBlue

@Composable
fun SecondSplashScreen(onNavigateToHome: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .clickable {onNavigateToHome() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally       ) {
            Image(
                painter = painterResource(id = R.drawable.snake),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Fit
            )
            Text(text = stringResource(R.string.welcome), color = DarkGreen, fontSize = 64.sp)
        }
    }
}

@Composable
@Preview
fun SecondSplashScreenPrev(){
    SecondSplashScreen(onNavigateToHome={})
}