package com.deezer.test.interfaces

import com.deezer.test.interfaces.data.Album
import com.deezer.test.interfaces.data.AlbumList

interface AlbumService {
    suspend fun getAlbumsList(index: Int): AlbumList
    suspend fun getAlbum(albumId: Int): Album
}