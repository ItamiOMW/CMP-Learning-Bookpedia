package com.itami.bookpedia.book_feature.domain.repository

import com.itami.bookpedia.book_feature.domain.model.Book
import com.itami.bookpedia.core.domain.DataError
import com.itami.bookpedia.core.domain.AppResult

interface BookRepository {

    suspend fun searchBooks(query: String): AppResult<List<Book>, DataError.Remote>

    suspend fun getBookDescription(bookId: String): AppResult<String?, DataError>

}