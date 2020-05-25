package com.deezer.test.interfaces.data

data class Album(
    val id: Int,
    val title: String,
    val cover_xl: String,
    val nb_tracks: Int,
    val release_date: String,
    val artist: Artist
)