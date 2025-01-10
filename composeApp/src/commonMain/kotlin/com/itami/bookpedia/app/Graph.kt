package com.itami.bookpedia.app

import kotlinx.serialization.Serializable

sealed interface Graph {

    @Serializable
    data object BooksGraph : Graph {

        @Serializable
        data object BooksListScreen

        @Serializable
        data class BookDetailsScreen(val bookId: String)

    }

}