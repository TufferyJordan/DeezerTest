package com.deezer.test.albumlist

import com.deezer.test.albumlist.domain.AlbumListInteractor
import com.deezer.test.albumlist.domain.AlbumListInteractorImpl
import com.deezer.test.albumlist.presenter.AlbumListPresenterImpl
import com.deezer.test.albumlist.presenter.AlbumListView
import com.deezer.test.albumlist.repository.AlbumListRepositoryImpl
import com.deezer.test.interfaces.AlbumStore
import kotlinx.coroutines.CoroutineScope

class AlbumListDependencies(
    view: AlbumListView,
    scope: CoroutineScope,
    store: AlbumStore
) {
    val interactor: AlbumListInteractor

    init {
        val presenter = AlbumListPresenterImpl(view)
        val repository = AlbumListRepositoryImpl(store)
        interactor = AlbumListInteractorImpl(repository, presenter, scope)
    }
}