package com.fahimezv.flickrfindr.core.database.di

import androidx.room.Room
import com.fahimezv.flickrfindr.core.database.FlickrDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            FlickrDatabase::class.java,
            "flickr-database"
        ).build()
    }

    single { get<FlickrDatabase>().termDao() }
    single { get<FlickrDatabase>().bookmarkDao() }
}