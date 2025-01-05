package com.example.feedthesnake.ui.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.R
import com.example.feedthesnake.ui.components.CustomButton
import com.example.feedthesnake.ui.theme.DarkGreen
import com.example.feedthesnake.ui.theme.DarkGrey
import com.example.feedthesnake.ui.theme.Green
import com.example.feedthesnake.ui.theme.LightBlue

@Composable
fun GameScreen() {
    Scaffold(
        topBar = { GameScreenTopBar() },
        content = { innerPadding -> GameScreenContent(modifier = Modifier.padding(innerPadding)) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreenTopBar() {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = LightBlue
    ), title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "Back",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(35.dp)
                )
            }
            Text(text="Score",color= DarkGrey)
            Box(
                modifier = Modifier
                    .background(color = Green, shape = RoundedCornerShape(8.dp)) // Arkaplan ve köşe yuvarlama
                    .border(BorderStroke(2.dp, DarkGreen), shape = RoundedCornerShape(8.dp)) // Sınır
                    .clickable { /* TODO: Tıklama işlemi */ } // Tıklama için
                    .padding(vertical = 12.dp), // İçerik boşluğu
                contentAlignment = Alignment.Center // Metni ortalamak için
            ) {
                Text(
                    text = "Score",
                    color = DarkGreen,
                    fontSize = 26.sp
                )
            }
        }
    }, actions = {

    })
}

@Composable
fun GameScreenContent(modifier: Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .clickable { }
    ) {

    }
}

@Composable
@Preview
fun GameScreenPrev() {
    GameScreen()
}