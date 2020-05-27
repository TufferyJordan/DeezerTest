package com.deezer.test.apirest

import com.deezer.test.interfaces.AlbumStore
import com.deezer.test.interfaces.AlbumService
import com.deezer.test.interfaces.data.Album
import com.deezer.test.interfaces.data.AlbumList

class AlbumStoreImpl(
    private val service: AlbumService
) : AlbumStore {
    private var lastRequest: AlbumList? = null
    override suspend fun getAlbumList(): AlbumList? {
        if(lastRequest == null) {
            lastRequest = service.getAlbumsList(0)
        }
        return lastRequest
    }

    override suspend fun getAlbum(albumId: Int): Album? {
        if(lastRequest == null) {
            lastRequest = service.getAlbumsList(0)
        }
        return lastRequest?.data?.find { it.id == albumId }
    }
}