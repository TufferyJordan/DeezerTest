package com.deezer.test.interfaces

import com.deezer.test.interfaces.data.Album
import com.deezer.test.interfaces.data.AlbumList

interface AlbumStore {
    suspend fun getAlbumList(): AlbumList?
    suspend fun getAlbum(albumId: Int): Album?
}