package com.deezer.test

import android.content.Context
import android.os.Build
import com.deezer.test.albumlist.view.AlbumListViewModel
import com.deezer.test.apirest.AlbumStoreImpl
import com.deezer.test.apirest.RetrofitAlbumService
import com.deezer.test.data.albumlist.GetAlbumList
import com.deezer.test.interfaces.AlbumService
import com.deezer.test.interfaces.AlbumStore
import com.deezer.test.interfaces.player.PlayerController
import com.deezer.test.interfaces.routing.AlbumListRouter
import com.deezer.test.routing.AlbumListRouterImpl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Modules {
    val data = module {
        single<AlbumService> {
            val baseUrl = if (Build.VERSION.SDK_INT <= 19) {
                "http://api.deezer.com/2.0/"
            } else {
                "https://api.deezer.com/2.0/"
            }

            val cacheSize = 5L * 1024L * 1024L
            val cache = Cache(get<Context>().cacheDir, cacheSize)

            val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitAlbumService::class.java)
        }
        single<AlbumStore> { AlbumStoreImpl(get()) }
    }

    val routing = module {
        single<AlbumListRouter> { AlbumListRouterImpl(get()) }
    }

    val repositories = module {
        single { GetAlbumList(get()) }
    }

    val viewModels = module {
        viewModel { AlbumListViewModel(get(), get()) }
    }

    val player = module {
        single<ExoPlayer> { SimpleExoPlayer.Builder(get()).build() }
        single<PlayerController> { ExoPlayerController(get(), get()) }
    }
}