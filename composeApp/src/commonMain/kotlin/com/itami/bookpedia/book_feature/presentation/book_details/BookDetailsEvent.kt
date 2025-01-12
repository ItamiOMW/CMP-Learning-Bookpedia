package com.itami.bookpedia.book_feature.presentation.book_details

sealed interface BookDetailsEvent {

    data object NavigateBack : BookDetailsEvent

}