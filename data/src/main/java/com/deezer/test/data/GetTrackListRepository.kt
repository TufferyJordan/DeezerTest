package com.deezer.test.data

import com.deezer.test.data.model.TrackData
import com.deezer.test.data.model.TrackListData
import com.deezer.test.interfaces.AlbumService
import java.io.IOException
import java.lang.IllegalArgumentException

class GetTrackListRepository(
    private val service: AlbumService
) {
    suspend fun getTrackList(albumId: Int): TrackListData? = try {
        TrackListData(service.getTrackList(albumId).data.map {
            TrackData(
                it.title,
                it.duration,
                it.preview,
                it.explicit_lyrics
            )
        })
    } catch (e: IOException) {
        null
    } catch (e: IllegalArgumentException) {
        null
    }
}