package com.example.feedthesnake.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.example.feedthesnake.constants.SizeConstants.MIN_FONT_SIZE
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.DarkGrey
import com.example.feedthesnake.theme.Green
import com.example.feedthesnake.theme.customFontFamily

@Composable
fun CustomRadioButton(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Green,
                unselectedColor = DarkGreen
            )
        )
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = label,
            fontFamily = customFontFamily,
            fontSize = MIN_FONT_SIZE,
            color = DarkGrey,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}