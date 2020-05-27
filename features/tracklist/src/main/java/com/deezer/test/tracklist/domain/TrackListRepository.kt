package com.deezer.test.tracklist.domain

import java.time.Duration

interface TrackListRepository {
    suspend fun getTrackList(albumId: Int): TrackListData?
}

data class TrackListData(
    val trackList: List<TrackData>
)

data class TrackData(
    val title: String,
    val duration: Int,
    val previewURL: String
)