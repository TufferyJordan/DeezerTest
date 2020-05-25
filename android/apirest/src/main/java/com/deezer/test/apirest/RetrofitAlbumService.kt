package com.deezer.test.apirest

import com.deezer.test.interfaces.AlbumService
import com.deezer.test.interfaces.data.Album
import com.deezer.test.interfaces.data.AlbumList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAlbumService: AlbumService {

    @GET("user/2529/albums")
    override suspend fun getAlbumsList(@Query("index") index: Int): AlbumList

    @GET("album/{albumId}")
    override suspend fun getAlbum(@Path("albumId") albumId: Int): Album
}