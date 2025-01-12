package com.itami.bookpedia.book_feature.presentation.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.bookpedia.book_feature.domain.repository.BookRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _events = Channel<BookDetailsEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(BookDetailsState())
    val state = _state.asStateFlow()

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

}