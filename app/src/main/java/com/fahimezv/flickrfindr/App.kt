package com.fahimezv.flickrfindr

import android.app.Application
import com.fahimezv.flickrfindr.core.database.di.databaseModule
import com.fahimezv.flickrfindr.presentaion.di.appKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {

        super.onCreate()

        // Setup Koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appKoinModule)
        }
    }
}