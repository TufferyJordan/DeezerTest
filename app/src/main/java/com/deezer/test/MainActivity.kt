package com.deezer.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.deezer.test.playerview.PlayerFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navigationBroadcastReceiver: NavigationBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(activityMainToolbar)
        val navController = findNavController(R.id.activityMainNavHostFragment)

        val appBarConfiguration = AppBarConfiguration.Builder(setOf(
                R.id.albumListFragment,
                R.id.githubFragment
        )).build()

        activityMainToolbar.setupWithNavController(navController, appBarConfiguration)
        activityMainBottomNavigationView.setupWithNavController(navController)

        navigationBroadcastReceiver = NavigationBroadcastReceiver(navController, this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.activityMainPlayerFragment, PlayerFragment.newInstance())
            .commitNow()
    }

    override fun onStart() {
        super.onStart()
        navigationBroadcastReceiver.registerReceiver()
    }

    override fun onStop() {
        navigationBroadcastReceiver.unregisterReceiver()
        super.onStop()
    }
}
