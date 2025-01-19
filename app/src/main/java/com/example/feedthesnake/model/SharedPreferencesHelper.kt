package com.example.feedthesnake.model

import android.content.Context
import android.content.SharedPreferences
object SharedPreferencesHelper {
    private const val PREFS_DIFFICULTY = "game_prefs"
    private const val KEY_DIFFICULTY = "difficulty"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_DIFFICULTY, Context.MODE_PRIVATE)
    }

    fun saveDifficulty(context: Context, difficulty:Long) {
        val preferences = getPreferences(context)
        preferences.edit().putLong(KEY_DIFFICULTY, difficulty).apply()
    }

    fun getDifficulty(context: Context): Long {
        val preferences = getPreferences(context)
        return preferences.getLong(KEY_DIFFICULTY, 0)
    }

}