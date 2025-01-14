package com.example.feedthesnake.ui.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.Green



@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onNavigate: (String?) -> Unit,
    name: String? = null,
    context: Context
) {

    Button(
        onClick = {
            if(text=="Exit"){
                (context as Activity).finishAffinity()
            }
            else{
            onNavigate(name)}},
        modifier = modifier
            .padding(start = 50.dp, end = 50.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Green),
        border = BorderStroke(2.dp, DarkGreen)
    ) {
        Text(text = text, color = DarkGreen, fontSize = 26.sp)
    }
}

