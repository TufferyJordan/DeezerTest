package com.deezer.test.tracklist.presenter

import com.deezer.test.tracklist.domain.TrackListViewModel


interface TrackListView {
    fun displayTrackList(viewModel: TrackListViewModel)
    fun displayError(message: String)
}