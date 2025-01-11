package com.example.feedthesnake.model

import android.content.Context
import android.content.SharedPreferences
object SharedPreferencesHelper {
    private const val PREFS_NAME = "game_prefs"
    private const val KEY_NAME = "name"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveName(context: Context, name: String) {
        val preferences = getPreferences(context)
        preferences.edit().putString(KEY_NAME, name).apply()
    }

    fun getName(context: Context): String {
        val preferences = getPreferences(context)
        return preferences.getString(KEY_NAME, "") ?: ""
    }

}