package com.deezer.test

import android.os.Build
import com.deezer.test.apirest.AlbumStoreImpl
import com.deezer.test.apirest.RetrofitAlbumService
import com.deezer.test.interfaces.AlbumService
import com.deezer.test.interfaces.AlbumStore
import com.deezer.test.interfaces.routing.AlbumListRouter
import com.deezer.test.routing.AlbumListRouterImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Modules {
    val data = module {
        val baseUrl = if(Build.VERSION.SDK_INT <= 19) {
            "http://api.deezer.com/2.0/"
        } else {
            "https://api.deezer.com/2.0/"
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        single<AlbumService> { retrofit.create(RetrofitAlbumService::class.java) }
        single<AlbumStore> { AlbumStoreImpl(get()) }
    }

    val routing = module {
        single<AlbumListRouter> { AlbumListRouterImpl(get()) }
    }
}