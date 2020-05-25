package com.deezer.test.albumlist.domain

interface AlbumListInteractor {
    fun load()
}

data class AlbumListDto(
    val list: List<AlbumDto>
)

data class AlbumDto(
    val id: Int,
    val title: String,
    val cover: String
)