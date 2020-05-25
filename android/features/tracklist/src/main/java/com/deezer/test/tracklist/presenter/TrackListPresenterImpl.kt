package com.deezer.test.tracklist.presenter

import com.deezer.test.tracklist.domain.TrackListDto
import com.deezer.test.tracklist.domain.TrackListPresenter
import com.deezer.test.tracklist.domain.TrackListViewModel
import com.deezer.test.tracklist.domain.TrackViewModel
import java.text.DecimalFormat
import java.text.NumberFormat

class TrackListPresenterImpl(
    private val view: TrackListView
) : TrackListPresenter {
    override fun presentTrackList(dto: TrackListDto) {
        view.displayTrackList(
            TrackListViewModel(
            dto.trackList.map {
                val f: NumberFormat = DecimalFormat("00")
                val duration = "${it.duration/60}:${f.format(it.duration%60)}"
                TrackViewModel(
                    it.title,
                    duration
                )
            }
        )
        )
    }

    override fun presentError(exception: Exception) {
        view.displayError(exception.message ?: "An error has occurred")
    }

    override fun presentLoading() {
        view.displayLoading()
    }
}