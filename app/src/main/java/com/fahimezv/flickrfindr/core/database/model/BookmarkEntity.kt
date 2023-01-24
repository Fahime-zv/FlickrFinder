package com.fahimezv.flickrfindr.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fahimezv.flickrfindr.core.model.Photo

@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "server")
    val server: String,
    @ColumnInfo(name = "secret")
    val secret: String,
    @ColumnInfo(name = "title")
    val title: String,
)

fun BookmarkEntity.toPhoto() = Photo(
    id = id,
    server = server,
    secret = secret,
    title = title
)
