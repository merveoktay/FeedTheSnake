package com.example.feedthesnake.ui.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feedthesnake.R
import com.example.feedthesnake.model.SharedPreferencesHelper
import com.example.feedthesnake.ui.components.CustomButton
import com.example.feedthesnake.ui.components.CustomTopBar
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.DarkGrey
import com.example.feedthesnake.theme.Green
import com.example.feedthesnake.theme.LightBlue

@Composable
fun NameEntryScreen(onNavigateToHome: () -> Unit,onNavigateToGame: () -> Unit ){
    val context = LocalContext.current
    Scaffold(containerColor = LightBlue,topBar = { CustomTopBar(onNavigateToHome)}, content = { innerPadding ->

        NameEntryScreenContent(context,modifier = Modifier.padding(innerPadding), onNavigateToGame)
    })
}

@Composable
fun NameEntryScreenContent(context: Context, modifier: Modifier, onNavigateToGame: () -> Unit) {

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
            Image(
                painter = painterResource(id = R.drawable.give_me_a_name),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(300.dp),

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
                colors = colors(
                    focusedBorderColor = Green,
                    unfocusedBorderColor = DarkGreen,
                    cursorColor = DarkGrey
                )
            )
            SharedPreferencesHelper.saveName(context, name)
            CustomButton(text = stringResource(R.string.save), onNavigate = onNavigateToGame)
        }
    }
}


@Composable
@Preview
fun NameEntryScreenPrev() {
    NameEntryScreen(onNavigateToHome={},onNavigateToGame={})
}