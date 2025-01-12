package com.itami.bookpedia.book_feature.presentation.book_details

import com.itami.bookpedia.book_feature.domain.model.Book

sealed interface BookDetailsAction {

    data object OnBackClick : BookDetailsAction

    data object OnFavoriteClick : BookDetailsAction

    data class OnSelectedBookChange(val book: Book): BookDetailsAction

}