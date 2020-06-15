package com.deezer.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import com.deezer.test.albumlist.AlbumListFragmentDirections
import com.deezer.test.routing.AlbumListRouterImpl

class NavigationBroadcastReceiver(
    private val navController: NavController,
    private val context: Context
): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        with(intent) {
            when (action) {
                AlbumListRouterImpl.ACTION_ALBUM_LIST_ROUTER -> {
                    val albumId = getIntExtra(AlbumListRouterImpl.ARGS_ALBUM_ID, -1)
                    if (albumId != -1) {
                        val direction = AlbumListFragmentDirections.albumListToAlbumDetail(albumId)
                        navController.navigate(direction)
                    }
                }
            }
        }
    }

    fun registerReceiver() {
        val broadcastManager = LocalBroadcastManager.getInstance(context)
        broadcastManager.registerReceiver(this, IntentFilter(AlbumListRouterImpl.ACTION_ALBUM_LIST_ROUTER))
    }

    fun unregisterReceiver() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this)
    }
}