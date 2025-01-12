package com.itami.bookpedia.book_feature.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {

    fun create(): RoomDatabase.Builder<BookpediaDatabase>

}