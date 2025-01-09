package com.example.feedthesnake.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.example.feedthesnake.ui.components.CustomButton
import com.example.feedthesnake.ui.components.CustomTopBar
import com.example.feedthesnake.ui.theme.DarkGrey
import com.example.feedthesnake.ui.theme.LightBlue

@Composable
fun GameOverScreen(score: Int,onNavigateToHome: () -> Unit,onNavigateToNameEntry: () -> Unit) {
    Scaffold(
        containerColor = LightBlue,
        topBar = { CustomTopBar(onNavigateToHome) },
        content = { innerPadding ->
            GameOverScreenContent(
                modifier = Modifier.padding(innerPadding),
                score = score,onNavigateToNameEntry
            )
        }
    )
}



@Composable
fun GameOverScreenContent(modifier: Modifier, score: Int,onNavigateToNameEntry: () -> Unit) {
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
            CustomButton(text = stringResource(R.string.try_again), onNavigate = onNavigateToNameEntry)
        }
    }
}

@Composable
@Preview
fun GameOverScreenPrev() {
    GameOverScreen(23,onNavigateToHome={},onNavigateToNameEntry={})
}
