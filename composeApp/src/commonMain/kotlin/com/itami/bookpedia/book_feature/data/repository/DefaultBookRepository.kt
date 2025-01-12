package com.itami.bookpedia.book_feature.data.repository

import androidx.sqlite.SQLiteException
import com.itami.bookpedia.book_feature.data.database.dao.FavoriteBookDao
import com.itami.bookpedia.book_feature.data.mapper.toBook
import com.itami.bookpedia.book_feature.data.mapper.toFavoriteBookEntity
import com.itami.bookpedia.book_feature.data.network.RemoteBookDataSource
import com.itami.bookpedia.book_feature.domain.model.Book
import com.itami.bookpedia.book_feature.domain.repository.BookRepository
import com.itami.bookpedia.core.domain.AppResult
import com.itami.bookpedia.core.domain.DataError
import com.itami.bookpedia.core.domain.EmptyResult
import com.itami.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
) : BookRepository {

    override suspend fun searchBooks(query: String): AppResult<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query = query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }

    }

    override fun observeFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .observeFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): AppResult<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)
        return if (localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookWorkId = bookId)
                .map { it.description }
        } else {
            AppResult.Success(localResult.description)
        }
    }

    override fun observeIsFavorite(bookId: String): Flow<Boolean> {
        return favoriteBookDao
            .observeFavoriteBook(bookId)
            .map { favoriteBook ->
                favoriteBook != null
            }

    }

    override suspend fun addToFavorites(book: Book): EmptyResult<DataError.Local> {
        return try {
            val bookEntity = book.toFavoriteBookEntity()
            favoriteBookDao.upsertFavoriteBook(bookEntity)
            AppResult.Success(Unit)
        } catch (e: SQLiteException) {
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(bookId: String) {
        favoriteBookDao.deleteFavoriteBook(bookId)
    }

}