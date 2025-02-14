package com.example.feedthesnake.util

import android.content.Context
import android.media.MediaPlayer

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null
    private var currentPosition: Int = 0

    var isMusicPlay = true

    fun playMusic(context: Context, musicResId: Int, loop: Boolean = true) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, musicResId).apply {
                isLooping = loop
                seekTo(currentPosition)
                start()
            }
        } else {
            mediaPlayer?.seekTo(currentPosition)
            mediaPlayer?.start()
        }
    }

    fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                currentPosition = it.currentPosition
                it.pause()
            }
        }
    }

    fun stopMusic() {
        mediaPlayer?.release()
        mediaPlayer = null
        currentPosition = 0
    }
}