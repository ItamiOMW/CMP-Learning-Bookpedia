package com.itami.bookpedia.book_feature.data.repository

import com.itami.bookpedia.book_feature.data.mapper.toBook
import com.itami.bookpedia.book_feature.data.network.RemoteBookDataSource
import com.itami.bookpedia.book_feature.domain.model.Book
import com.itami.bookpedia.book_feature.domain.repository.BookRepository
import com.itami.bookpedia.core.domain.AppResult
import com.itami.bookpedia.core.domain.DataError
import com.itami.bookpedia.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {

    override suspend fun searchBooks(query: String): AppResult<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query = query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }

    }

    override suspend fun getBookDescription(bookId: String): AppResult<String?, DataError> {
        return remoteBookDataSource
            .getBookDetails(bookWorkId = bookId)
            .map { it.description }
    }

}