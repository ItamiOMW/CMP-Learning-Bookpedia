package com.itami.bookpedia.book_feature.data.mapper

import com.itami.bookpedia.book_feature.data.database.entity.FavoriteBookEntity
import com.itami.bookpedia.book_feature.data.dto.SearchedBookDto
import com.itami.bookpedia.book_feature.domain.model.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = if(coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}

fun Book.toFavoriteBookEntity() = FavoriteBookEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    imageUrl = this.imageUrl,
    languages = this.languages,
    authors = this.authors,
    firstPublishYear = this.firstPublishYear,
    ratingAvg = this.averageRating,
    ratingsCount = this.ratingCount,
    numPagesMedian = this.numPages,
    numEditions = this.numEditions,
)

fun FavoriteBookEntity.toBook() = Book(
    id = this.id,
    title = this.title,
    description = this.description,
    imageUrl = this.imageUrl,
    languages = this.languages,
    authors = this.authors,
    firstPublishYear = this.firstPublishYear,
    averageRating = this.ratingAvg,
    ratingCount = this.ratingsCount,
    numPages = this.numPagesMedian,
    numEditions = this.numEditions,
)