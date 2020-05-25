package com.deezer.test.albumdetail.domain

interface AlbumDetailRepository {
    suspend fun getAlbumDetail(albumId: Int): AlbumDetailData
}

data class AlbumDetailData(
    val coverImage: String,
    val albumName: String,
    val tracksNumber: Int,
    val artistImage: String?,
    val artistName: String,
    val albumReleaseDate: String
)