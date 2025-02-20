package com.example.feedthesnake.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants.DIVIDER_THICKNESS_SIZE
import com.example.feedthesnake.constants.SizeConstants.ICON_SIZE
import com.example.feedthesnake.constants.SizeConstants.MAX_FONT_SIZE
import com.example.feedthesnake.constants.SizeConstants.MAX_PADDING_SIZE
import com.example.feedthesnake.constants.SizeConstants.MEDIUM_FONT_SIZE
import com.example.feedthesnake.constants.SizeConstants.MEDIUM_PADDING_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_FONT_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_PADDING_SIZE
import com.example.feedthesnake.constants.SizeConstants.MODIFIER_SIZE
import com.example.feedthesnake.constants.SizeConstants.WEIGHT_SIZE
import com.example.feedthesnake.model.Snake
import com.example.feedthesnake.theme.DarkGrey
import com.example.feedthesnake.theme.LightBlue
import com.example.feedthesnake.viewModel.SnakeViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ScoreTableScreen(onNavigateToHome: () -> Unit,snakeViewModel:SnakeViewModel) {
    val snakes=snakeViewModel.topSnakes
    Scaffold(
        modifier = Modifier.testTag("ScoreTableScreen"),
        containerColor = LightBlue,
        topBar = { ScoreTableScreenTopBar(onNavigateToHome) },
        content = { innerPadding -> ScoreTableScreenContent(modifier = Modifier.padding(innerPadding),snakes) }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreTableScreenTopBar(onNavigateToHome: () -> Unit) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = LightBlue
    ), title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onNavigateToHome() }) {
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = stringResource(R.string.back),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(ICON_SIZE)
                )
            }
            Spacer(modifier = Modifier.weight(WEIGHT_SIZE))
            Text(
                text = stringResource(R.string.score_table),
                color = DarkGrey,
                fontSize = MAX_FONT_SIZE,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = MIN_PADDING_SIZE)
            )
            Spacer(modifier = Modifier.weight(WEIGHT_SIZE))
            Icon(
                painter = painterResource(id = R.drawable.score_icon),
                contentDescription = stringResource(R.string.score),
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(MODIFIER_SIZE)
                    .padding(end = MEDIUM_PADDING_SIZE)
            )

        }
    }
    )
}

@Composable
fun ScoreTableScreenContent(modifier: Modifier, snakes: StateFlow<List<Snake>>) {
    val snakeList by snakes.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start= MAX_PADDING_SIZE,end = MAX_PADDING_SIZE),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.name),
                fontSize = MEDIUM_FONT_SIZE,
                fontWeight = FontWeight.Bold,
                color = DarkGrey
            )
            Text(
                text = stringResource(R.string.score),
                fontSize =MEDIUM_FONT_SIZE,
                fontWeight = FontWeight.Bold,
                color = DarkGrey
            )
        }

        Divider(color = Color.Black, thickness = DIVIDER_THICKNESS_SIZE)
        LazyColumn( verticalArrangement = Arrangement.spacedBy(MAX_PADDING_SIZE),modifier = Modifier.fillMaxSize()){
            items(snakeList) { snake ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = snake.name,
                        fontSize = MIN_FONT_SIZE,
                        color = DarkGrey
                    )
                    Text(
                        text = snake.score.toString(),
                        fontSize = MIN_FONT_SIZE,
                        color = DarkGrey
                    )

                }

            }
        }
    }
}

