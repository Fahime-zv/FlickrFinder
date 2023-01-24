package com.fahimezv.flickrfindr.core.network.model

import com.fahimezv.flickrfindr.core.model.Photo
import com.google.gson.annotations.SerializedName

data class PhotoDao(
    @SerializedName("id") val id: String,
    @SerializedName("server") val server: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("title") val title: String,
)

val PhotoDao.thumbnailUrl: String
    get() = toFlickerPhotoUrl(id, server, secret, SuffixClass.THUMBNAIL)

val PhotoDao.imageUrl: String
    get() = toFlickerPhotoUrl(id, server, secret, SuffixClass.MEDIUM)

private enum class SuffixClass(val suffix: String) {
    THUMBNAIL("q"),
    MEDIUM("c"),
}

private fun toFlickerPhotoUrl(
    id: String,
    server: String,
    secret: String,
    suffixClass: SuffixClass,
) = "https://live.staticflickr.com/${server}/${id}_${secret}_${suffixClass.suffix}.jpg"

fun PhotoDao.toPhoto() = Photo(
    id = id,
    server = server,
    secret = secret,
    title = title
)