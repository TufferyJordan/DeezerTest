package com.deezer.test.apirest

import com.deezer.test.interfaces.AlbumListService
import com.deezer.test.interfaces.data.AlbumRoot
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAlbumListService: AlbumListService {

    @GET("user/2529/albums")
    override suspend fun getAlbums(@Query("index") index: Int): AlbumRoot
}