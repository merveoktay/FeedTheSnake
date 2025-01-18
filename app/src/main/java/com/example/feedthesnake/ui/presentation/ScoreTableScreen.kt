package com.example.feedthesnake.ui.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.R
import com.example.feedthesnake.model.Snake
import com.example.feedthesnake.theme.DarkGrey
import com.example.feedthesnake.theme.LightBlue
import com.example.feedthesnake.viewModel.SnakeViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ScoreTableScreen(onNavigateToHome: () -> Unit,snakeViewModel:SnakeViewModel) {
    val snakes=snakeViewModel.topSnakes
    Scaffold(
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
                    modifier = Modifier.size(35.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.score_table),
                color = DarkGrey,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.score_icon),
                contentDescription = stringResource(R.string.score),
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(55.dp)
                    .padding(end = 20.dp)
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
            .padding(horizontal = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 15.dp, start = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Name",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGrey
            )
            Text(
                text = "Score",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGrey
            )
        }

        Divider(color = Color.Black, thickness = 1.dp)
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 3.dp)
        ) {

            items(snakeList) { snake ->
                Log.d("Listeleme",snake.name)
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(end = 15.dp, start = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = snake.name,
                        fontSize = 16.sp,
                        color = DarkGrey
                    )
                    Text(
                        text = snake.score.toString(),
                        fontSize = 16.sp,
                        color = DarkGrey
                    )
                }
            }
        }
    }
}

