package com.deezer.test.albumdetail.domain

interface AlbumDetailPresenter {
    fun presentAlbumDetail(dto: AlbumDetailDto)
    fun presentError(exception: Exception)
}

data class AlbumDetailViewModel(
    val coverImage: String,
    val albumName: String,
    val artistImage: String,
    val artistName: String,
    val albumDetail: String,
    val explicit: Boolean
)