package com.deezer.test.albumlist.presenter

import com.deezer.test.albumlist.view.AlbumListView
import com.deezer.test.albumlist.domain.AlbumListDto

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
}