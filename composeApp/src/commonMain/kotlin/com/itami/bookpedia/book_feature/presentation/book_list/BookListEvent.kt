package com.itami.bookpedia.book_feature.presentation.book_list

import com.itami.bookpedia.book_feature.domain.Book

sealed interface BookListEvent {

    data class OnNavigateToBookDetails(val book: Book) : BookListEvent

}