package com.fahimezv.flickrfindr.core.model

import android.os.Parcelable
import com.fahimezv.flickrfindr.core.network.model.PhotoDao
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val server: String,
    val secret: String,
    val title: String,
) : Parcelable


val Photo.thumbnail: String
    get() = toFlickerPhotoUrl(id, server, secret, SuffixClass.THUMBNAIL)

val Photo.image: String
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
