package com.fahimezv.flickrfindr.presentaion.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fahimezv.flickrfindr.core.data.repository.SearchRepository
import com.fahimezv.flickrfindr.core.data.repository.TermRepository
import com.fahimezv.flickrfindr.core.model.Photo
import com.fahimezv.flickrfindr.core.model.Term
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val termRepository: TermRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Success(PagingData.empty()))
    val uiState = _uiState.asStateFlow()

    val terms = termRepository.getTerms()

    fun search(term: String) {
        if (term.isBlank()) return
        viewModelScope.launch {
            termRepository.addTerm(Term(text = term))
            searchUiState(
                term = term,
                scope = viewModelScope,
                searchRepository = searchRepository
            ).collectLatest { _uiState.value = it }
        }
    }


}

private fun searchUiState(
    term: String,
    scope: CoroutineScope,
    searchRepository: SearchRepository,
): Flow<SearchUiState> {
    return searchRepository.search(term)
        .cachedIn(scope)
        .map {
            val uiState: SearchUiState = SearchUiState.Success(it)
            uiState
        }
        .onStart { emit(SearchUiState.Loading) }
        .catch { emit(SearchUiState.Error) }
}

sealed interface SearchUiState {
    data class Success(val photos: PagingData<Photo>) : SearchUiState
    object Error : SearchUiState
    object Loading : SearchUiState
}


