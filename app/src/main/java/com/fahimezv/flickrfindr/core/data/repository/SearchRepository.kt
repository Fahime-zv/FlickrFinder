package com.fahimezv.flickrfindr.core.data.repository

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.fahimezv.flickrfindr.core.data.source.SearchPagingSource
import com.fahimezv.flickrfindr.core.model.Photo
import com.fahimezv.flickrfindr.core.network.NetworkDataSource
import com.fahimezv.flickrfindr.core.network.model.PhotoDao
import com.fahimezv.flickrfindr.core.network.model.toPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

interface SearchRepository {

    fun search(term: String): Flow<PagingData<Photo>>

}

class SearchRepositoryImpl(private val networkDataSource: NetworkDataSource) : SearchRepository {

    override fun search(term: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPagingSource(
                    networkDataSource = networkDataSource,
                    term = term
                )
            }
        )
            .flow
            .map { pagingData: PagingData<PhotoDao> ->
                pagingData.map { it.toPhoto() }
            }
    }

}