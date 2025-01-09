package com.example.feedthesnake.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.feedthesnake.ui.theme.DarkGreen
import com.example.feedthesnake.ui.theme.DarkGrey
import com.example.feedthesnake.ui.theme.Green
import com.example.feedthesnake.ui.theme.LightBlue

@Composable
fun NameEntryScreen(onNavigateToHome: () -> Unit,onNavigateToGame: () -> Unit ){
    Scaffold(containerColor = LightBlue,topBar = { CustomTopBar(onNavigateToHome)}, content = { innerPadding ->

        NameEntryScreenContent(modifier = Modifier.padding(innerPadding), onNavigateToGame)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameEntryScreenContent(modifier: Modifier,onNavigateToGame: () -> Unit) {
    var name by remember { mutableStateOf("") }
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
                text = stringResource(R.string.give_me_a_name),
                color = DarkGrey,
                fontSize = 64.sp,
                modifier = modifier.padding(start = 50.dp, end = 50.dp, bottom = 50.dp)
            )
            OutlinedTextField(
                value = name,
                onValueChange = { newName ->
                    name = newName
                },
                label = { Text(stringResource(R.string.name), color = DarkGrey) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp, top = 10.dp, bottom = 10.dp),
                singleLine = true,
                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Green,
                    unfocusedBorderColor = DarkGreen,
                    cursorColor = DarkGrey
                )
            )
            CustomButton(text = stringResource(R.string.save), onNavigate = onNavigateToGame)
        }
    }
}


@Composable
@Preview
fun NameEntryScreenPrev() {
    NameEntryScreen(onNavigateToHome={},onNavigateToGame={})
}