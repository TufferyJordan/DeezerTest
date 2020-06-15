package com.deezer.test.data

import com.deezer.test.data.model.AlbumData
import com.deezer.test.interfaces.AlbumStore
import java.io.IOException

class GetAlbumRepository(
    private val store: AlbumStore
) {
    suspend fun getAlbumDetail(albumId: Int): AlbumData? = try {
        store.getAlbum(albumId)?.let {
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
    } catch (e: IOException) {
        null
    }
}