package com.example.feedthesnake.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants.ICON_SIZE
import com.example.feedthesnake.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(onNavigate: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LightBlue
        ), title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onNavigate() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = stringResource(R.string.back),
                        tint = Color.Unspecified,
                        modifier = Modifier.size(ICON_SIZE)
                    )
                }
            }
        }
    )
}