package com.itami.bookpedia.book_feature.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itami.bookpedia.book_feature.data.database.converter.StringListTypeConverter
import com.itami.bookpedia.book_feature.data.database.dao.FavoriteBookDao
import com.itami.bookpedia.book_feature.data.database.entity.FavoriteBookEntity

@Database(
    entities = [FavoriteBookEntity::class, ],
    version = 1,
)
@TypeConverters(
    StringListTypeConverter::class,
)
abstract class BookpediaDatabase : RoomDatabase() {

    abstract val favoriteBookDao: FavoriteBookDao

    companion object {
        const val DB_NAME = "bookpedia.db"
    }

}