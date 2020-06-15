package com.deezer.test.data.model

data class AlbumData(
    val id: Int,
    val coverImage: String,
    val albumName: String,
    val tracksNumber: Int,
    val artistImage: String,
    val artistName: String,
    val albumReleaseDate: String,
    val explicit: Boolean
)