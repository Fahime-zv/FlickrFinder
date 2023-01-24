package com.fahimezv.flickrfindr.core.network.model

import com.google.gson.annotations.SerializedName


data class SearchDao(
    @SerializedName("photos") val photos: PhotosDao,
)
