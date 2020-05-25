package com.deezer.test.albumdetail.domain

import com.deezer.test.albumdetail.domain.AlbumDetailDto

interface AlbumDetailPresenter {
    fun presentAlbumDetail(dto: AlbumDetailDto)
}

data class AlbumDetailViewModel(
    val coverImage: String,
    val albumName: String,
    val tracksNumber: Int,
    val artistImage: String?,
    val artistName: String,
    val albumReleaseDate: String
)