package com.example.feedthesnake.ui.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants.IMAGE_MAX_SIZE
import com.example.feedthesnake.constants.SizeConstants.MAX_FONT_SIZE
import com.example.feedthesnake.constants.SizeConstants.MAX_PADDING_SIZE
import com.example.feedthesnake.ui.components.CustomButton
import com.example.feedthesnake.ui.components.CustomTopBar
import com.example.feedthesnake.theme.DarkGrey
import com.example.feedthesnake.theme.LightBlue
import com.example.feedthesnake.theme.customFontFamily

@Composable
fun GameOverScreen(score: Int,onNavigateToHome: () -> Unit,onNavigateToNameEntry: (String?) -> Unit) {
    val context = LocalContext.current
    Scaffold(modifier = Modifier.testTag("GameOverScreen"),
        containerColor = LightBlue,
        topBar = { CustomTopBar(onNavigateToHome) },
        content = { innerPadding ->
            GameOverScreenContent(
                modifier = Modifier.padding(innerPadding),
                score = score,onNavigateToNameEntry,
                context=context
            )
        }
    )
}



@Composable
fun GameOverScreenContent(
    modifier: Modifier,
    score: Int,
    onNavigateToNameEntry: (String?) -> Unit,
    context: Context
) {
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
            Image(
                painter = painterResource(id = R.drawable.game_over),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(IMAGE_MAX_SIZE),

            )
            Text(
                text = stringResource(R.string.your_score, score),
                color = DarkGrey,
                fontFamily = customFontFamily,
                fontSize = MAX_FONT_SIZE,
                modifier = modifier.padding(start = MAX_PADDING_SIZE, end = MAX_PADDING_SIZE)
            )
            CustomButton(
                text = stringResource(R.string.try_again),
                onNavigate =onNavigateToNameEntry ,
                context = context
            )
        }
    }
}


