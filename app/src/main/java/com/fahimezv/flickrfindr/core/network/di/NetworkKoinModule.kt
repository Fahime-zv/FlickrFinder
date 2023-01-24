package com.fahimezv.flickrfindr.core.network.di

import com.fahimezv.flickrfindr.core.network.NetworkDataSource
import com.fahimezv.flickrfindr.core.network.retrofit.RetrofitNetworkDataSource
import org.koin.dsl.module

val networkKoinModule = module {

    factory <NetworkDataSource> { RetrofitNetworkDataSource() }

}