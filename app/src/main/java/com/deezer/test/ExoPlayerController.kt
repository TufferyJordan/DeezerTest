package com.deezer.test

import android.content.Context
import android.net.Uri
import com.deezer.test.interfaces.player.PlayerController
import com.deezer.test.interfaces.player.PlayerListener
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


class ExoPlayerController(
    private val player: ExoPlayer,
    private val context: Context
) : PlayerController {
    private val listenersMap = HashMap<PlayerListener, Player.EventListener>()

    override var currentAlbumTitle: String? = null

    override fun play(albumTitle: String, sourceURL: String) {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, "DeezerTest")
        )
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(sourceURL))
        player.prepare(videoSource)
        player.playWhenReady = true
        currentAlbumTitle = albumTitle
    }

    override fun resumePause() {
        player.playWhenReady = !player.playWhenReady
    }

    override fun stop() {
        player.stop(true)
    }

    override fun registerListener(listener: PlayerListener) {
        val playerListener = object: Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if(playWhenReady) {
                    listener.onPlayPlayer()
                } else {
                    listener.onPausePlayer()
                }
                if(playbackState == Player.STATE_ENDED ||
                    playbackState == Player.STATE_IDLE) {
                    listener.onStopPlayer()
                }
            }
        }

        listenersMap[listener] = playerListener
        player.addListener(playerListener)
    }

    override fun unregisterListener(listener: PlayerListener) {
        listenersMap[listener]?.let {
            player.removeListener(it)
        }
    }

}