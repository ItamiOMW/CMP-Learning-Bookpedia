package com.itami.bookpedia.book_feature.presentation.book_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.itami.bookpedia.app.Graph
import com.itami.bookpedia.book_feature.domain.repository.BookRepository
import com.itami.bookpedia.core.domain.onError
import com.itami.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _events = Channel<BookDetailsEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(BookDetailsState())
    val state = _state
        .onStart {
            fetchBookDescription()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    fun onAction(action: BookDetailsAction) {
        when (action) {
            is BookDetailsAction.OnBackClick -> {
                viewModelScope.launch {
                    _events.send(BookDetailsEvent.NavigateBack)
                }
            }

            is BookDetailsAction.OnFavoriteClick -> {
                // TODO change favorite
            }

            is BookDetailsAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(book = action.book)
                }
            }
        }
    }

    private fun fetchBookDescription() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val bookId = savedStateHandle.toRoute<Graph.BooksGraph.BookDetailsScreen>().bookId
            bookRepository
                .getBookDescription(bookId)
                .onSuccess { desc ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(description = desc),
                            isLoading = false
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }
        }
    }

}