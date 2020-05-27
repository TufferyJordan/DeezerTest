package com.deezer.test.interfaces.player

interface PlayerController {
    var currentAlbumTitle: String?
    fun play(albumTitle: String, sourceURL: String)
    fun resumePause()
    fun stop()
    fun registerListener(listener: PlayerListener)
    fun unregisterListener(listener: PlayerListener)
}