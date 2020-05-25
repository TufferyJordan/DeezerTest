package com.deezer.test.albumdetail.domain

interface AlbumDetailInteractor {
    fun load(albumId: Int)
}

data class AlbumDetailDto(
    val coverImage: String,
    val albumName: String,
    val tracksNumber: Int,
    val artistImage: String?,
    val artistName: String,
    val albumReleaseDate: String
)