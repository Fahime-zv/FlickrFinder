package com.fahimezv.flickrfindr.core.model

data class Photos(
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val total: Int,
    val list: List<Photo>,
)