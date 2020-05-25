package com.deezer.test.albumlist.repository

import com.deezer.test.albumlist.domain.AlbumData
import com.deezer.test.albumlist.domain.AlbumListData
import com.deezer.test.albumlist.domain.AlbumListRepository
import com.deezer.test.interfaces.AlbumService

class AlbumListRepositoryImpl(
    private val service: AlbumService
) : AlbumListRepository {
    override suspend fun getAlbumList(): AlbumListData =
        AlbumListData(service.getAlbumsList(0).data.map {
            AlbumData(
                it.id,
                it.title,
                it.cover_xl
            )
        })
}