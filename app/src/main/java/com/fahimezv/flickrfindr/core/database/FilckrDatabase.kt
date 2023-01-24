package com.fahimezv.flickrfindr.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fahimezv.flickrfindr.core.database.dao.BookmarkDao
import com.fahimezv.flickrfindr.core.database.dao.TermDao
import com.fahimezv.flickrfindr.core.database.model.BookmarkEntity
import com.fahimezv.flickrfindr.core.database.model.TermEntity

@Database(
    entities = [
        TermEntity::class,
        BookmarkEntity::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class FlickrDatabase : RoomDatabase() {
    abstract fun termDao(): TermDao
    abstract fun bookmarkDao(): BookmarkDao
}
