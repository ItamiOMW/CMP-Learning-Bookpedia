package com.itami.bookpedia.book_feature.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.itami.bookpedia.book_feature.data.database.entity.FavoriteBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {

    @Query("SELECT * FROM favorite_books")
    fun observeFavoriteBooks(): Flow<List<FavoriteBookEntity>>

    @Query("SELECT * FROM favorite_books WHERE id = :id LIMIT 1")
    fun observeFavoriteBook(id: String): Flow<FavoriteBookEntity?>

    @Query("SELECT * FROM favorite_books WHERE id = :id LIMIT 1")
    suspend fun getFavoriteBook(id: String): FavoriteBookEntity?

    @Upsert
    suspend fun upsertFavoriteBook(book: FavoriteBookEntity)

    @Query("DELETE FROM favorite_books WHERE id = :id")
    suspend fun deleteFavoriteBook(id: String)

}