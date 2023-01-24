package com.fahimezv.flickrfindr.core.network.model

import com.fahimezv.flickrfindr.core.model.Photos
import com.google.gson.annotations.SerializedName

data class PhotosDao(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("perpage") val perPage: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("photo") val list: List<PhotoDao>,
)

fun PhotosDao.toPhotos() = Photos(
    page = page,
    pages = pages,
    perPage = perPage,
    total = total,
    list = list.map { it.toPhoto() }
)