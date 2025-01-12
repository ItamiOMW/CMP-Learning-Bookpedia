package com.itami.bookpedia.book_feature.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String,
    val languages: List<String>,
    val authors: List<String>,
    val firstPublishYear: String?,
    val ratingAvg: Double?,
    val ratingsCount: Int?,
    val numPagesMedian: Int?,
    val numEditions: Int,
)
