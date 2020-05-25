package com.deezer.test.albumlist.domain

import java.lang.Exception

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

class AlbumListException: Exception("An error has occurred while requesting the album list")