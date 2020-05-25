package com.deezer.test.tracklist.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TrackListInteractorImpl(
    private val repository: TrackListRepository,
    private val presenter: TrackListPresenter,
    private val viewScope: CoroutineScope
) : TrackListInteractor {

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    override fun load(albumId: Int) {
        presenter.presentLoading()
        ioScope.launch {
            val data = repository.getTrackList(albumId)
            viewScope.launch {
                data?.let {
                    presenter.presentTrackList(
                        TrackListDto(
                            data.trackList.map {
                                TrackDto(
                                    it.title,
                                    it.duration
                                )
                            }
                        )
                    )
                } ?: run {
                    presenter.presentError(TrackListException())
                }
            }
        }
    }
}