package com.deezer.test.data.albumlist

import com.deezer.test.data.model.AlbumData
import com.deezer.test.data.model.AlbumListData
import com.deezer.test.interfaces.AlbumStore
import java.io.IOException

class GetAlbumList(
    private val store: AlbumStore
) {
    suspend fun getAlbumList(): AlbumListData? = try {
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