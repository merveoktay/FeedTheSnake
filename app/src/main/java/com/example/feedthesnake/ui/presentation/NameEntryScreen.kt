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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants.IMAGE_MIN_SIZE
import com.example.feedthesnake.constants.SizeConstants.MAX_CORNER_SHAPE_SIZE
import com.example.feedthesnake.constants.SizeConstants.MEDIUM_FONT_SIZE
import com.example.feedthesnake.constants.SizeConstants.MEDIUM_PADDING_SIZE
import com.example.feedthesnake.constants.SpeedConstants.EASY_SPEED
import com.example.feedthesnake.constants.SpeedConstants.HARD_SPEED
import com.example.feedthesnake.constants.SpeedConstants.NORMAL_SPEED
import com.example.feedthesnake.model.SharedPreferencesHelper
import com.example.feedthesnake.ui.components.CustomButton
import com.example.feedthesnake.ui.components.CustomTopBar
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.DarkGrey
import com.example.feedthesnake.theme.Green
import com.example.feedthesnake.theme.LightBlue
import com.example.feedthesnake.theme.customFontFamily
import com.example.feedthesnake.ui.components.CustomRadioButton

@Composable
fun NameEntryScreen(onNavigateToHome: () -> Unit, onNavigateToGame: (String) -> Unit) {
    val context = LocalContext.current
    Scaffold(modifier = Modifier.testTag("NameEntryScreen").wrapContentSize(),
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
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.give_me_a_name),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(IMAGE_MIN_SIZE),

                )
            OutlinedTextField(
                value = name,
                onValueChange = { newName ->
                    name = newName
                },

                label = { Text(stringResource(R.string.name), color = DarkGrey,fontFamily = customFontFamily) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MEDIUM_PADDING_SIZE),
                singleLine = true,
                textStyle = TextStyle(
                    fontFamily = customFontFamily,
                    color = DarkGrey
                ),
                shape = RoundedCornerShape(MAX_CORNER_SHAPE_SIZE),
                colors = colors(
                    focusedBorderColor = Green,
                    unfocusedBorderColor = DarkGreen,
                    cursorColor = DarkGrey,
                    focusedLabelColor = DarkGrey,
                    unfocusedLabelColor = DarkGrey,
                    focusedTextColor = DarkGrey,
                    disabledTextColor = DarkGrey

                )
            )
            Text(
                text = stringResource(R.string.select_difficulty),
                fontSize = MEDIUM_FONT_SIZE,
                fontWeight = FontWeight.Bold,
                fontFamily = customFontFamily,
                color = DarkGrey,
                modifier = Modifier.padding(top = MEDIUM_PADDING_SIZE)
            )
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
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
                easy -> EASY_SPEED
                normal -> NORMAL_SPEED
                hard -> HARD_SPEED
                else -> NORMAL_SPEED
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


