package com.fahimezv.flickrfindr.core.network

import com.fahimezv.flickrfindr.core.network.model.SearchDao

interface NetworkDataSource {

    suspend fun search(
        term: String,
        page: Int,
        perPage: Int
    ): SearchDao

}