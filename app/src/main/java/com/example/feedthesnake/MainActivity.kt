package com.example.feedthesnake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.feedthesnake.navigation.AppNavHost
import com.example.feedthesnake.theme.FeedTheSnakeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedTheSnakeTheme {
                AppNavHost()
            }
        }
    }
}

