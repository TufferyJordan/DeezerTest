package com.deezer.test.tracklist.presenter

import android.content.res.Resources
import com.deezer.test.design.R
import com.deezer.test.tracklist.domain.TrackListDto
import com.deezer.test.tracklist.domain.TrackListPresenter
import com.deezer.test.tracklist.domain.TrackListViewModel
import com.deezer.test.tracklist.domain.TrackViewModel

class TrackListPresenterImpl(
    private val view: TrackListView
) : TrackListPresenter {
    override fun presentTrackList(dto: TrackListDto) {
        view.displayTrackList(
            TrackListViewModel(
            dto.trackList.map {
                TrackViewModel(
                    it.title,
                    it.duration
                )
            }
        )
        )
    }

    override fun presentError(exception: Exception) {
        view.displayError(exception.message ?: "An error has occurred")
    }
}