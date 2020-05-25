package com.deezer.test.interfaces.data

data class AlbumRoot(
    val data: List<Album>,
    val total: Int,
    val artist: Artist
)