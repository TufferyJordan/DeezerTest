package com.deezer.test.albumdetail

import com.deezer.test.albumdetail.domain.AlbumDetailInteractor
import com.deezer.test.albumdetail.domain.AlbumDetailInteractorImpl
import com.deezer.test.albumdetail.presenter.AlbumDetailPresenterImpl
import com.deezer.test.albumdetail.presenter.AlbumDetailView
import com.deezer.test.albumdetail.repository.AlbumDetailRepositoryImpl
import com.deezer.test.interfaces.AlbumService
import kotlinx.coroutines.CoroutineScope

class AlbumDetailDependencies(
    view: AlbumDetailView,
    scope: CoroutineScope,
    service: AlbumService
) {
    val interactor: AlbumDetailInteractor

    init {
        val presenter = AlbumDetailPresenterImpl(view)
        val repository = AlbumDetailRepositoryImpl(service)
        interactor = AlbumDetailInteractorImpl(repository, presenter, scope)
    }
}