package com.deezer.test.albumdetail.domain

import java.lang.Exception

interface AlbumDetailInteractor {
    fun load(albumId: Int)
}

data class AlbumDetailDto(
    val coverImage: String,
    val albumName: String,
    val tracksNumber: Int,
    val artistImage: String,
    val artistName: String,
    val albumReleaseDate: String,
    val explicit: Boolean
)

class AlbumDetailException: Exception("An error has occurred during the album request")