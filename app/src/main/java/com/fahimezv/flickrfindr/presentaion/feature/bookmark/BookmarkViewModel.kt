package com.fahimezv.flickrfindr.presentaion.feature.bookmark

import androidx.lifecycle.ViewModel
import com.fahimezv.flickrfindr.core.data.repository.BookmarkRepository

class BookmarkViewModel(
    bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    val bookmarks = bookmarkRepository.getAll()

}