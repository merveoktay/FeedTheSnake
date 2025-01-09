package com.example.feedthesnake.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.R
import com.example.feedthesnake.ui.theme.DarkGreen
import com.example.feedthesnake.ui.theme.Green


@Composable
fun CustomButton(modifier: Modifier = Modifier, text: String,onNavigate: () -> Unit) {

    Button(
        onClick = {onNavigate()},
        modifier = modifier
            .padding(start = 50.dp, end = 50.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Green),
        border = BorderStroke(2.dp, DarkGreen)
    ) {
        Text(text = text, color = DarkGreen, fontSize = 26.sp)
    }
}

@Composable
@Preview
fun CustomButtonPrev() {
    CustomButton(text = stringResource(R.string.new_game), onNavigate = {})
}