package com.deezer.test.tracklist.domain

interface TrackListPresenter {
    fun presentTrackList(dto: TrackListDto)
    fun presentError(exception: Exception)
    fun presentLoading()
}

data class TrackListViewModel(
    val trackList: List<TrackViewModel>
)

data class TrackViewModel(
    val title: String,
    val duration: String,
    val previewURL: String
)