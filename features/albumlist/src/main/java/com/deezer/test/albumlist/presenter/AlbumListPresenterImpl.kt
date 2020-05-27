package com.deezer.test.albumlist.presenter

import com.deezer.test.albumlist.domain.*

class AlbumListPresenterImpl(
    private val view: AlbumListView
) : AlbumListPresenter {
    override fun presentAlbumList(dto: AlbumListDto) {
        view.displayAlbumList(
            AlbumListViewModel(dto.list.map {
                AlbumViewModel(
                    it.id,
                    it.title,
                    it.cover
                )
            })
        )
    }

    override fun presentError(exception: AlbumListException) {
        view.displayError(exception.message ?: "")
    }

    override fun presentLoading() {
        view.displayLoading()
    }
}