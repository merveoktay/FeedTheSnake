@file:Suppress("UNCHECKED_CAST")

package com.example.feedthesnake.ui.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants
import com.example.feedthesnake.constants.SpeedConstants
import com.example.feedthesnake.model.SharedPreferencesHelper
import com.example.feedthesnake.ui.components.CustomButton
import com.example.feedthesnake.ui.components.CustomTopBar
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.DarkGrey
import com.example.feedthesnake.theme.Green
import com.example.feedthesnake.theme.LightBlue
import com.example.feedthesnake.ui.components.CustomRadioButton

@Composable
fun NameEntryScreen(onNavigateToHome: () -> Unit, onNavigateToGame: (String) -> Unit) {
    val context = LocalContext.current
    Scaffold(
        containerColor = LightBlue,
        topBar = { CustomTopBar(onNavigateToHome) },
        content = { innerPadding ->

            NameEntryScreenContent(
                context,
                modifier = Modifier.padding(innerPadding),
                onNavigateToGame
            )
        })
}

@Composable
fun NameEntryScreenContent(
    context: Context,
    modifier: Modifier,
    onNavigateToGame: (String) -> Unit,
) {
    val easy= stringResource(R.string.easy)
    val normal= stringResource(R.string.normal)
    val hard= stringResource(R.string.hard)
    var selectedDifficulty by remember { mutableStateOf(normal) }
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.give_me_a_name),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(SizeConstants.IMAGE_MIN_SIZE),

                )
            OutlinedTextField(
                value = name,
                onValueChange = { newName ->
                    name = newName
                },
                label = { Text(stringResource(R.string.name), color = DarkGrey) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SizeConstants.MAX_PADDING_SIZE, SizeConstants.MAX_PADDING_SIZE),
                singleLine = true,
                shape = RoundedCornerShape(SizeConstants.MAX_CORNER_SHAPE_SIZE),
                colors = colors(
                    focusedBorderColor = Green,
                    unfocusedBorderColor = DarkGreen,
                    cursorColor = DarkGrey
                )
            )
            Text(
                text = stringResource(R.string.select_difficulty),
                fontSize = SizeConstants.MEDIUM_FONT_SIZE,
                fontWeight = FontWeight.Bold,
                color = DarkGrey,
                modifier = Modifier.padding(top = SizeConstants.MEDIUM_PADDING_SIZE)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SizeConstants.MAX_PADDING_SIZE, vertical = SizeConstants.SMALL_PADDING_SIZE),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CustomRadioButton(
                    label = stringResource(R.string.easy),
                    selected = selectedDifficulty == stringResource(R.string.easy),
                    onClick = { selectedDifficulty = easy }
                )
                CustomRadioButton(
                    label = stringResource(R.string.normal),
                    selected = selectedDifficulty == stringResource(R.string.normal),
                    onClick = { selectedDifficulty = normal }
                )
                CustomRadioButton(
                    label = stringResource(R.string.hard),
                    selected = selectedDifficulty == stringResource(R.string.hard),
                    onClick = { selectedDifficulty = hard }
                )
            }
            val speed = when (selectedDifficulty) {
                easy -> SpeedConstants.EASY_SPEED
                normal -> SpeedConstants.NORMAL_SPEED
                hard -> SpeedConstants.HARD_SPEED
                else -> SpeedConstants.NORMAL_SPEED
            }
            SharedPreferencesHelper.saveDifficulty(context, speed)
            if (name.isNotEmpty()) {
                CustomButton(
                    text = stringResource(R.string.start),
                    onNavigate = onNavigateToGame as (String?) -> Unit,
                    name = name,
                    context = context
                )
            }
        }
    }
}


