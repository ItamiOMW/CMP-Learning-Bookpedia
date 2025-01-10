package com.itami.bookpedia.book_feature.domain.model

data class Book(
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String,
    val authors: List<String>,
    val languages: List<String>,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int
)