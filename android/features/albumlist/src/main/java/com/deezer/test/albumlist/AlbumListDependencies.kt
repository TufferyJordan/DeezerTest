package com.deezer.test.albumlist

import com.deezer.test.albumlist.domain.AlbumListInteractor
import com.deezer.test.albumlist.domain.AlbumListInteractorImpl
import com.deezer.test.albumlist.presenter.AlbumListPresenterImpl
import com.deezer.test.albumlist.repository.AlbumListRepositoryImpl
import com.deezer.test.albumlist.view.AlbumListView
import com.deezer.test.interfaces.AlbumListService
import kotlinx.coroutines.CoroutineScope

class AlbumListDependencies(
    view: AlbumListView,
    scope: CoroutineScope,
    service: AlbumListService
) {
    val interactor: AlbumListInteractor

    init {
        val presenter = AlbumListPresenterImpl(view)
        val repository = AlbumListRepositoryImpl(service)
        interactor = AlbumListInteractorImpl(repository, presenter, scope)
    }
}