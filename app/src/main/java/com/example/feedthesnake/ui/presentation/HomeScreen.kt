package com.example.feedthesnake.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feedthesnake.R
import com.example.feedthesnake.ui.components.CustomButton

@Composable
fun HomeScreen(onNavigateToNameEntry: () -> Unit, onNavigateToScoreTable: () -> Unit) {
    Scaffold(content = { innerPadding ->
        HomeScreenContent(
            modifier = Modifier.padding(innerPadding),
            onNavigateToNameEntry,
            onNavigateToScoreTable
        )

    })
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    onNavigateToNameEntry: () -> Unit,
    onNavigateToScoreTable: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = stringResource(R.string.logo),
            modifier = modifier.fillMaxSize(1f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_icon),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Fit
            )
            CustomButton(
                text = stringResource(R.string.new_game),
                onNavigate = onNavigateToNameEntry
            )
            CustomButton(
                text = stringResource(R.string.high_score),
                onNavigate = onNavigateToScoreTable
            )
            CustomButton(text = stringResource(R.string.exit), onNavigate = {})

        }
    }

}


@Composable
@Preview
fun HomeScreenPrev() {
    HomeScreen(onNavigateToNameEntry = {}, onNavigateToScoreTable = {})
}