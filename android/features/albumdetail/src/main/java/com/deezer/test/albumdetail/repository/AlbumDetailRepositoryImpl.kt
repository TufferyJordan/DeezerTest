package com.deezer.test.albumdetail.repository

import com.deezer.test.albumdetail.domain.AlbumDetailData
import com.deezer.test.albumdetail.domain.AlbumDetailRepository
import com.deezer.test.interfaces.AlbumStore
import java.io.IOException

class AlbumDetailRepositoryImpl(
    private val store: AlbumStore
) : AlbumDetailRepository {
    override suspend fun getAlbumDetail(albumId: Int): AlbumDetailData? = try {
        store.getAlbum(albumId)?.let {
            AlbumDetailData(
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