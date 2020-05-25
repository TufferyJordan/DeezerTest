package com.deezer.test.albumlist.repository

import com.deezer.test.albumlist.domain.AlbumData
import com.deezer.test.albumlist.domain.AlbumListData
import com.deezer.test.albumlist.domain.AlbumListRepository
import com.deezer.test.interfaces.AlbumStore
import java.io.IOException

class AlbumListRepositoryImpl(
    private val store: AlbumStore
) : AlbumListRepository {
    override suspend fun getAlbumList(): AlbumListData? = try {
        val data = store.getAlbumList()?.data?.map {
            AlbumData(
                it.id,
                it.title,
                it.cover_xl
            )
        }
        data?.let {
            AlbumListData(it)
        }
    } catch (e: IOException) {
        null
    }
}