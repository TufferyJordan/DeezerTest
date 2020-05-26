package com.deezer.test.tracklist.domain

import java.lang.Exception

interface TrackListInteractor {
    fun load(albumId: Int)
}

data class TrackListDto(
    val trackList: List<TrackDto>
)

data class TrackDto(
    val title: String,
    val duration: Int,
    val previewURL: String
)

class TrackListException: Exception("An error has occurred during the tracklist request")