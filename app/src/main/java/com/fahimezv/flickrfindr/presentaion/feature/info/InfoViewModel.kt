package com.fahimezv.flickrfindr.presentaion.feature.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahimezv.flickrfindr.core.data.repository.BookmarkRepository
import com.fahimezv.flickrfindr.core.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InfoViewModel(
    private val photo: Photo,
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    private val _bookmarkFlow = MutableStateFlow(false)
    val bookmarkFlow = _bookmarkFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _bookmarkFlow.value = bookmarkRepository.isBookmarked(photo)
        }
    }

    fun bookmark() {
        viewModelScope.launch {
            bookmarkRepository.bookmark(photo)
        }
    }

    fun unBookmark() {
        viewModelScope.launch {
            bookmarkRepository.unBookmark(photo)
        }
    }

}