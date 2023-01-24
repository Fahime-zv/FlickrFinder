package com.fahimezv.flickrfindr.core.data.repository

import android.nfc.tech.MifareUltralight
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.fahimezv.flickrfindr.core.database.dao.BookmarkDao
import com.fahimezv.flickrfindr.core.database.model.BookmarkEntity
import com.fahimezv.flickrfindr.core.database.model.toPhoto
import com.fahimezv.flickrfindr.core.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface BookmarkRepository {
    suspend fun bookmark(photo: Photo)
    suspend fun unBookmark(photo: Photo)
    suspend fun isBookmarked(photo: Photo): Boolean
    fun getAll(): Flow<PagingData<Photo>>
}

class BookmarkRepositoryImpl(private val bookmarkDao: BookmarkDao): BookmarkRepository {

    override suspend fun bookmark(photo: Photo) {
        bookmarkDao.insert(photo.toBookmarkEntity())
    }

    override suspend fun unBookmark(photo: Photo) {
        bookmarkDao.delete(photo.toBookmarkEntity())
    }

    override suspend fun isBookmarked(photo: Photo): Boolean {
        return bookmarkDao.isExist(photo.id)
    }

    override fun getAll(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = MifareUltralight.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { bookmarkDao.getBookmarks() }
        )
            .flow
            .map { pagingData: PagingData<BookmarkEntity> ->
                pagingData.map { it.toPhoto() }
            }
    }

    private fun Photo.toBookmarkEntity() = BookmarkEntity(
        id = id,
        server = server,
        secret = secret,
        title = title
    )

}