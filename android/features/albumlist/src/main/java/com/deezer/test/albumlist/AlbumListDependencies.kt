package com.deezer.test.albumlist

import com.deezer.test.albumlist.domain.AlbumListInteractor
import com.deezer.test.albumlist.domain.AlbumListInteractorImpl
import com.deezer.test.albumlist.presenter.AlbumListPresenterImpl
import com.deezer.test.albumlist.repository.AlbumListRepositoryImpl
import com.deezer.test.albumlist.presenter.AlbumListView
import com.deezer.test.interfaces.AlbumService
import kotlinx.coroutines.CoroutineScope

class AlbumListDependencies(
    view: AlbumListView,
    scope: CoroutineScope,
    service: AlbumService
) {
    val interactor: AlbumListInteractor

    init {
        val presenter = AlbumListPresenterImpl(view)
        val repository = AlbumListRepositoryImpl(service)
        interactor = AlbumListInteractorImpl(repository, presenter, scope)
    }
}