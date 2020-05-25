package com.deezer.test.albumlist.domain

interface AlbumListPresenter {
    fun presentAlbumList(dto: AlbumListDto)
    fun presentError(exception: AlbumListException)
}

data class AlbumListViewModel(
    val list: List<AlbumViewModel>
)
data class AlbumViewModel(
    val id: Int,
    val title: String,
    val cover: String
)