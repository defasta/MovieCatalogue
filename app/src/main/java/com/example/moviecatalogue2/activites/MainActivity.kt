package com.example.moviecatalogue2.activites

import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.adapters.SectionsPagerAdapter
import com.example.moviecatalogue2.notifications.MovieDailyReceiver
import com.example.moviecatalogue2.notifications.MovieReleaseReceiver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.fragment_favorite.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieDailyReceiver: MovieDailyReceiver
    private lateinit var movieReleaseReceiver : MovieReleaseReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_movie, R.id.navigation_tvshow, R.id.navigation_favorite
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        movieDailyReceiver  = MovieDailyReceiver()
        movieReleaseReceiver = MovieReleaseReceiver()

        movieDailyReceiver.setAlarm(this)
        movieReleaseReceiver.setAlarm(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }else if (item.itemId == R.id.action_reminder_settings){
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}
