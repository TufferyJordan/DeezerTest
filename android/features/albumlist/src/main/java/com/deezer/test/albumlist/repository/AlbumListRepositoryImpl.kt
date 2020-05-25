package com.deezer.test.albumlist.repository

import com.deezer.test.interfaces.AlbumListService

class AlbumListRepositoryImpl(
    private val service: AlbumListService
) : AlbumListRepository {
    override suspend fun getAlbumList(): AlbumListData =
        AlbumListData(service.getAlbums(0).data.map {
            AlbumData(
                it.id,
                it.title,
                it.cover_xl
            )
        })
}