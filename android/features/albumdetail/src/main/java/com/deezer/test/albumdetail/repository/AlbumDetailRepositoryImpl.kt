package com.deezer.test.albumdetail.repository

import com.deezer.test.albumdetail.domain.AlbumDetailData
import com.deezer.test.albumdetail.domain.AlbumDetailRepository
import com.deezer.test.interfaces.AlbumService

class AlbumDetailRepositoryImpl(
    private val service: AlbumService
) : AlbumDetailRepository {
    override suspend fun getAlbumDetail(albumId: Int): AlbumDetailData = service.getAlbum(albumId).let {
        AlbumDetailData(
            it.cover_xl,
            it.title,
            it.nb_tracks,
            it.artist.picture,
            it.artist.name,
            it.release_date
        )
    }
}