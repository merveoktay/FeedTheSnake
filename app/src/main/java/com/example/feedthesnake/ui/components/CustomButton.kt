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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.Green


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onNavigate: (String?) -> Unit,
    name: String? = null,
    context: Context,
) {
    val buttonText = stringResource(R.string.exit)
    Button(
        onClick = {
            if (text == buttonText) {
                (context as Activity).finishAffinity()
            } else {
                onNavigate(name)
            }
        },
        modifier = modifier.testTag(text)
            .padding(
                start = SizeConstants.MAX_PADDING_SIZE,
                end = SizeConstants.MAX_PADDING_SIZE,
                top = SizeConstants.SMALL_PADDING_SIZE,
                bottom = SizeConstants.SMALL_PADDING_SIZE
            )
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Green),
        border = BorderStroke(SizeConstants.BUTTON_CORNER_SHAPE_SIZE, DarkGreen)
    ) {
        Text(text = text, color = DarkGreen, fontSize = SizeConstants.BUTTON_FONT_SIZE)
    }
}

