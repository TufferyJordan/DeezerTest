package com.deezer.test.data.albumlist

import com.deezer.test.data.model.AlbumData
import com.deezer.test.data.model.AlbumListData
import com.deezer.test.interfaces.AlbumStore
import java.io.IOException

class GetAlbumListRepository(
    private val store: AlbumStore
) {
    suspend fun getAlbumList(): AlbumListData? = try {
        val data = store.getAlbumList()?.data?.map {
            AlbumData(
                it.id,
                it.cover_xl,
                it.title,
                it.nb_tracks,
                it.artist.picture_medium,
                it.artist.name,
                it.release_date,
                it.explicit_lyrics
            )
        }
        data?.let {
            AlbumListData(it)
        }
    } catch (e: IOException) {
        null
    }
}