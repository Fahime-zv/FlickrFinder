package com.fahimezv.flickrfindr.core.database.dao

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.*
import com.fahimezv.flickrfindr.core.database.model.BookmarkEntity
import com.fahimezv.flickrfindr.core.database.model.TermEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmarkEntity: BookmarkEntity)

    @Delete
    suspend fun delete(bookmarkEntity: BookmarkEntity)

    @Query(value = "SELECT EXISTS(SELECT * FROM bookmark WHERE id = :id)")
    suspend fun isExist(id: String): Boolean

    @Query(value = "SELECT * FROM bookmark")
    fun getBookmarks(): PagingSource<Int, BookmarkEntity>

}