package com.itami.bookpedia.book_feature.domain.repository

import com.itami.bookpedia.book_feature.domain.model.Book
import com.itami.bookpedia.core.domain.DataError
import com.itami.bookpedia.core.domain.AppResult
import com.itami.bookpedia.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun searchBooks(query: String): AppResult<List<Book>, DataError.Remote>

    fun observeFavoriteBooks(): Flow<List<Book>>

    suspend fun getBookDescription(bookId: String): AppResult<String?, DataError>

    fun observeIsFavorite(bookId: String): Flow<Boolean>

    suspend fun addToFavorites(book: Book): EmptyResult<DataError.Local>

    suspend fun deleteFromFavorites(bookId: String)

}