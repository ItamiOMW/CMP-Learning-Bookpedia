package com.itami.bookpedia.book_feature.data.network

import com.itami.bookpedia.book_feature.data.dto.SearchResponseDto
import com.itami.bookpedia.core.data.safeCall
import com.itami.bookpedia.core.domain.DataError
import com.itami.bookpedia.core.domain.AppResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient,
) : RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): AppResult<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/search.json") {
                parameter("query", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
            }
        }
    }

}