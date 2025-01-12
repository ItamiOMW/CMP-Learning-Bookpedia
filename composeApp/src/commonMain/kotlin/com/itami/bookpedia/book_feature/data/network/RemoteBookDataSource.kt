package com.itami.bookpedia.book_feature.data.network

import com.itami.bookpedia.book_feature.data.dto.BookWorkDto
import com.itami.bookpedia.book_feature.data.dto.SearchResponseDto
import com.itami.bookpedia.core.domain.DataError
import com.itami.bookpedia.core.domain.AppResult

interface RemoteBookDataSource {

    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): AppResult<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): AppResult<BookWorkDto, DataError.Remote>

}