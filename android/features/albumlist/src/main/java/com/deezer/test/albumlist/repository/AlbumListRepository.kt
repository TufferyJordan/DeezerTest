package com.deezer.test.albumlist.repository

interface AlbumListRepository {
    suspend fun getAlbumList(): AlbumListData
}

data class AlbumListData(
    val list: List<AlbumData>
)

data class AlbumData(
    val id: Int,
    val title: String,
    val cover: String
)