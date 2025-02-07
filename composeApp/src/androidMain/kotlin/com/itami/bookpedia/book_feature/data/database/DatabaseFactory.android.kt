package com.itami.bookpedia.book_feature.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {

    actual fun create(): RoomDatabase.Builder<BookpediaDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(BookpediaDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }

}