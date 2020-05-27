package com.deezer.test.tracklist.repository

import com.deezer.test.interfaces.AlbumService
import com.deezer.test.interfaces.AlbumStore
import com.deezer.test.interfaces.data.TrackList
import com.deezer.test.tracklist.domain.TrackData
import com.deezer.test.tracklist.domain.TrackListData
import com.deezer.test.tracklist.domain.TrackListRepository
import java.io.IOException
import java.lang.IllegalArgumentException

class TrackListRepositoryImpl(
    private val service: AlbumService
) : TrackListRepository {
    override suspend fun getTrackList(albumId: Int): TrackListData? = try {
        TrackListData(service.getTrackList(albumId).data.map {
            TrackData(
                it.title,
                it.duration,
                it.preview
            )
        })
    } catch (e: IOException) {
        null
    } catch (e: IllegalArgumentException) {
        null
    }
}