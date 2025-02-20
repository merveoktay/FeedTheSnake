package com.example.feedthesnake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.feedthesnake.navigation.AppNavHost
import com.example.feedthesnake.theme.FeedTheSnakeTheme
import com.example.feedthesnake.util.MusicManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MusicManager.playMusic(this, R.raw.intro_music)
        setContent {
            FeedTheSnakeTheme {
                AppNavHost()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        MusicManager.pauseMusic()
    }

    override fun onResume() {
        super.onResume()
        if (MusicManager.isMusicPlay)
            MusicManager.playMusic(this, R.raw.intro_music)
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicManager.stopMusic()
    }

}

