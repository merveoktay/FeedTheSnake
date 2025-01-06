package com.example.feedthesnake.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.R
import com.example.feedthesnake.ui.theme.LightBlue

@Composable
fun ScoreTableScreen() {
    Scaffold(
        containerColor = LightBlue,
        topBar = { ScoreTableScreenTopBar() },
        content = { innerPadding -> ScoreTableScreenContent(modifier = Modifier.padding(innerPadding)) }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreTableScreenTopBar() {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
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
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.score_table),
                color = Color.Black,
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
fun ScoreTableScreenContent(modifier: Modifier) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.name),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 2.dp, start = 50.dp)
            )
            Spacer(modifier = modifier.weight(1f))
            Text(
                text = stringResource(R.string.score),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 2.dp, end = 50.dp)
            )
        }
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .width(1.dp)
                .padding(horizontal = 3.dp)
                .padding(start = 15.dp, end = 15.dp)
                .align(Alignment.CenterHorizontally),
            color = Color.Black)
        LazyColumn {

        }
    }

}

@Composable
@Preview
fun ScoreTableScreenPrev() {
    ScoreTableScreen()
}