package com.itami.bookpedia.book_feature.presentation.book_list

import com.itami.bookpedia.book_feature.domain.model.Book
import com.itami.bookpedia.core.presentation.utils.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null,
)
