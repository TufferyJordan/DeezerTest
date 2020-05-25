package com.deezer.test.interfaces

import com.deezer.test.interfaces.data.Album
import com.deezer.test.interfaces.data.AlbumList
import com.deezer.test.interfaces.data.TrackList

interface AlbumService {
    suspend fun getAlbumsList(index: Int): AlbumList
    suspend fun getTrackList(albumId: Int): TrackList
}