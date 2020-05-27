package com.deezer.test.routing

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.deezer.test.interfaces.routing.AlbumListRouter

class AlbumListRouterImpl(private val context: Context): AlbumListRouter {
    override fun navigate(albumId: Int) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent().apply {
            action = ACTION_ALBUM_LIST_ROUTER
            putExtra(ARGS_ALBUM_ID, albumId)
        })
    }
    companion object {
        const val ACTION_ALBUM_LIST_ROUTER = "ACTION_ALBUM_LIST_ROUTER"
        const val ARGS_ALBUM_ID = "AlbumListRouterImpl.ARGS_ALBUM_ID"
    }
}