package com.deezer.test

import com.deezer.test.apirest.RetrofitAlbumListService
import com.deezer.test.interfaces.AlbumListService
import com.deezer.test.interfaces.routing.AlbumListRouter
import com.deezer.test.routing.AlbumListRouterImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Modules {
    val data = module {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.deezer.com/2.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val albumListService: AlbumListService =
            retrofit.create(RetrofitAlbumListService::class.java)
        single { albumListService }
    }

    val routing = module {
        single<AlbumListRouter> { AlbumListRouterImpl(get()) }
    }
}