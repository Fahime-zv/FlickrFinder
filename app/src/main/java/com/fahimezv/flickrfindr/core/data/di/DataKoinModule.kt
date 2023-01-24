package com.fahimezv.flickrfindr.core.data.di

import com.fahimezv.flickrfindr.core.data.repository.*
import com.fahimezv.flickrfindr.core.database.di.databaseModule
import org.koin.dsl.module

val dataKoinModule = module {
    includes(databaseModule)

    factory<SearchRepository> { SearchRepositoryImpl(networkDataSource = get()) }

    factory<TermRepository> { TermRepositoryImpl(termDao = get()) }

    factory<BookmarkRepository> { BookmarkRepositoryImpl(bookmarkDao = get()) }
}