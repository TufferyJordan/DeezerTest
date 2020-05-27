package com.deezer.test.albumdetail

import android.content.Context
import android.content.res.Resources
import com.deezer.test.albumdetail.domain.AlbumDetailInteractor
import com.deezer.test.albumdetail.domain.AlbumDetailInteractorImpl
import com.deezer.test.albumdetail.presenter.AlbumDetailPresenterImpl
import com.deezer.test.albumdetail.presenter.AlbumDetailView
import com.deezer.test.albumdetail.repository.AlbumDetailRepositoryImpl
import com.deezer.test.interfaces.AlbumStore
import kotlinx.coroutines.CoroutineScope

class AlbumDetailDependencies(
    view: AlbumDetailView,
    scope: CoroutineScope,
    store: AlbumStore,
    resources: Resources
) {
    val interactor: AlbumDetailInteractor

    init {
        val presenter = AlbumDetailPresenterImpl(view, resources)
        val repository = AlbumDetailRepositoryImpl(store)
        interactor = AlbumDetailInteractorImpl(repository, presenter, scope)
    }
}