package com.itami.bookpedia.book_feature.presentation.book_details

import com.itami.bookpedia.book_feature.domain.model.Book

data class BookDetailsState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null,
)
