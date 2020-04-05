package com.example.moviecatalogue2.activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.notifications.MovieDailyReceiver
import com.example.moviecatalogue2.notifications.MovieReleaseReceiver
import com.example.moviecatalogue2.preferences.SettingsPreferences
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    companion object{
        private lateinit var movieDailyReceiver: MovieDailyReceiver
        @SuppressLint("StaticFieldLeak")
        private lateinit var movieReleaseReceiver: MovieReleaseReceiver
        private lateinit var settingsPreferences: SettingsPreferences

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.setTitle("Settings")

        movieDailyReceiver = MovieDailyReceiver()
        movieReleaseReceiver = MovieReleaseReceiver()
        settingsPreferences = SettingsPreferences(this)

        setSwitchRelease()
        setSwitchReminder()

        switch_daily.setOnClickListener{
            if (switch_daily.isChecked) {
                movieDailyReceiver.setAlarm(applicationContext)
                settingsPreferences.setDailyReminder(true)
            } else {
                movieDailyReceiver.cancelAlarm(applicationContext)
                settingsPreferences.setDailyReminder(false)
            }
        }

        switch_release.setOnClickListener{
            if(switch_release.isChecked){
                movieReleaseReceiver.setAlarm(applicationContext)
                settingsPreferences.setReleaseReminder(true)
            }else{
                movieReleaseReceiver.cancelAlarm(applicationContext)
                settingsPreferences.setReleaseReminder(false)
            }
        }

    }

    private fun setSwitchReminder(){
        switch_daily.isChecked = settingsPreferences.getDailyReminder()
    }

    private fun setSwitchRelease(){
        switch_release.isChecked = settingsPreferences.getReleaseReminder()
    }
}
