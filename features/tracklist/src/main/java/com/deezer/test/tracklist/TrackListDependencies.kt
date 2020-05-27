package com.deezer.test.tracklist

import android.content.res.Resources
import com.deezer.test.interfaces.AlbumService
import com.deezer.test.interfaces.AlbumStore
import com.deezer.test.tracklist.domain.TrackListInteractor
import com.deezer.test.tracklist.domain.TrackListInteractorImpl
import com.deezer.test.tracklist.presenter.TrackListPresenterImpl
import com.deezer.test.tracklist.presenter.TrackListView
import com.deezer.test.tracklist.repository.TrackListRepositoryImpl
import kotlinx.coroutines.CoroutineScope

class TrackListDependencies(
    view: TrackListView,
    scope: CoroutineScope,
    service: AlbumService
) {
    val interactor: TrackListInteractor

    init {
        val presenter = TrackListPresenterImpl(view)
        val repository = TrackListRepositoryImpl(service)
        interactor = TrackListInteractorImpl(repository, presenter, scope)
    }
}