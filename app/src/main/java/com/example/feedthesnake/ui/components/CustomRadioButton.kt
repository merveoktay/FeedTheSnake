package com.example.feedthesnake.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.feedthesnake.constants.SizeConstants.MIN_FONT_SIZE
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.DarkGrey
import com.example.feedthesnake.theme.Green

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
            text = label,
            fontSize = MIN_FONT_SIZE,
            color = DarkGrey
        )
    }
}