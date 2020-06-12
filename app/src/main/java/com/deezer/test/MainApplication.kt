package com.deezer.test

import android.app.Application
import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(Modules.data, Modules.routing, Modules.player, Modules.repositories, Modules.viewModels)
        }
    }
}