package com.deezer.test.interfaces

import com.deezer.test.interfaces.data.AlbumRoot

interface AlbumListService {
    suspend fun getAlbums(index: Int): AlbumRoot
}