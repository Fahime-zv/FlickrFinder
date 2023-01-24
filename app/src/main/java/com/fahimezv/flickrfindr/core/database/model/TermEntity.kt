package com.fahimezv.flickrfindr.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fahimezv.flickrfindr.core.model.Term

@Entity(tableName = "term")
data class TermEntity(
    @PrimaryKey
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
)

fun TermEntity.toTerm() = Term(text = text)
