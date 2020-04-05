package com.example.moviecatalogue2.preferences

import android.content.Context
import android.content.SharedPreferences

class SettingsPreferences(context: Context) {

    companion object{
        private const val PREFERENCE_NAME = "setting"
        private const val DAILY_REMINDER = "isDaily"
        private const val RELEASE_REMINDER = "isRelease"
        private lateinit var sharedPreferences: SharedPreferences
    }

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun setDailyReminder(isActive: Boolean){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(DAILY_REMINDER, isActive)
        editor.apply()
    }

    fun setReleaseReminder(isActive: Boolean){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(RELEASE_REMINDER, isActive)
        editor.apply()
    }

    fun getDailyReminder():Boolean{
        return sharedPreferences.getBoolean(DAILY_REMINDER, false)
    }

    fun getReleaseReminder(): Boolean{
        return sharedPreferences.getBoolean(RELEASE_REMINDER, false)
    }
}