package com.example.feedthesnake.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.R
import com.example.feedthesnake.ui.components.CustomButton
import com.example.feedthesnake.ui.theme.DarkGrey
import com.example.feedthesnake.ui.theme.LightBlue

@Composable
fun GameOverScreen(score: Int) {
    Scaffold(
        containerColor = LightBlue,
        topBar = { GameOverScreenTopBar() },
        content = { innerPadding ->
            GameOverScreenContent(
                modifier = Modifier.padding(innerPadding),
                score = score
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameOverScreenTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LightBlue
        ), title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = stringResource(R.string.back),
                        tint = Color.Unspecified,
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun GameOverScreenContent(modifier: Modifier, score: Int) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = stringResource(R.string.logo),
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.game_over),
                color = DarkGrey,
                fontSize = 58.sp,
                modifier = modifier.padding(start = 50.dp, end = 50.dp, bottom = 50.dp)
            )
            Text(
                text = stringResource(R.string.your_score, score),
                color = DarkGrey,
                fontSize = 32.sp,
                modifier = modifier.padding(start = 50.dp, end = 50.dp, bottom = 50.dp)
            )
            CustomButton(text = stringResource(R.string.try_again))
        }
    }
}

@Composable
@Preview
fun GameOverScreenPrev() {
    GameOverScreen(23)
}
