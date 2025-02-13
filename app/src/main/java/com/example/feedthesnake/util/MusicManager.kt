package com.example.feedthesnake.util

import android.content.Context
import android.media.MediaPlayer

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null

    fun playMusic(context: Context, musicResId: Int, loop: Boolean = true) {
        stopMusic()
        mediaPlayer = MediaPlayer.create(context, musicResId).apply {
            isLooping = loop
            start()
        }
    }

    fun stopMusic() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}