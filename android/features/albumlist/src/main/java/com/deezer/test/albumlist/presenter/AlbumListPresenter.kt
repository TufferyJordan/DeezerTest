package com.deezer.test.albumlist.presenter

import com.deezer.test.albumlist.domain.AlbumListDto

interface AlbumListPresenter {
    fun presentAlbumList(dto: AlbumListDto)
}

data class AlbumListViewModel(
    val list: List<AlbumViewModel>
)
data class AlbumViewModel(
    val id: Int,
    val title: String,
    val cover: String
)