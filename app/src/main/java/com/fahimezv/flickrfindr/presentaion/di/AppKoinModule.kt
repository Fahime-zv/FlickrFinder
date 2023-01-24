package com.fahimezv.flickrfindr.presentaion.di

import com.fahimezv.flickrfindr.core.data.di.dataKoinModule
import com.fahimezv.flickrfindr.core.network.di.networkKoinModule
import com.fahimezv.flickrfindr.presentaion.feature.bookmark.BookmarkViewModel
import com.fahimezv.flickrfindr.presentaion.feature.info.InfoViewModel
import com.fahimezv.flickrfindr.presentaion.feature.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appKoinModule = module {

    includes(networkKoinModule)
    includes(dataKoinModule)

    viewModel { SearchViewModel(searchRepository = get(), termRepository = get()) }

    viewModel { parameters ->
        InfoViewModel(photo = parameters.get(), bookmarkRepository = get())
    }

    viewModel {
        BookmarkViewModel(bookmarkRepository = get())
    }
}