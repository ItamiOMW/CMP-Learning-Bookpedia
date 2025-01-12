package com.itami.bookpedia.book_feature.data.database

import androidx.room.RoomDatabaseConstructor

// Room will generate actual implementations, so just suppress NO_ACTUAL_FOR_EXPECT
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object BookpediaDbConstructor : RoomDatabaseConstructor<BookpediaDatabase> {

    override fun initialize(): BookpediaDatabase

}