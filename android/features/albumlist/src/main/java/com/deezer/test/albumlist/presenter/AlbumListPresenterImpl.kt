package com.deezer.test.albumlist.presenter

import com.deezer.test.albumlist.domain.AlbumListDto
import com.deezer.test.albumlist.domain.AlbumListPresenter
import com.deezer.test.albumlist.domain.AlbumListViewModel
import com.deezer.test.albumlist.domain.AlbumViewModel

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