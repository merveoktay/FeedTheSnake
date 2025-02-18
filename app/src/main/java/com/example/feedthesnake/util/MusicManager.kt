package com.example.feedthesnake.util

import android.content.Context
import android.media.MediaPlayer

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null
    private var currentPosition: Int = 0

    var isMusicPlay = true

    fun playMusic(
        context: Context,
        musicResId: Int,
        loop: Boolean = true,
        onComplete: (() -> Unit)? = null,
    ) {
        stopMusic()
        if (currentPosition != 0) {
            mediaPlayer = MediaPlayer.create(context, musicResId).apply {
                isLooping = loop
                seekTo(currentPosition)
                start()
                setOnCompletionListener {
                    onComplete?.invoke()
                }
            }
        } else {
            mediaPlayer = MediaPlayer.create(context, musicResId).apply {
                isLooping = loop
                seekTo(0)
                start()
                setOnCompletionListener {
                    onComplete?.invoke()
                }
            }
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
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        currentPosition = 0
    }
}
