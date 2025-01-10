package com.itami.bookpedia

import android.app.Application
import com.itami.bookpedia.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookpediaApp : Application() {

    override fun onCreate() {
        initKoin {
            androidContext(this@BookpediaApp)
        }
        super.onCreate()
    }
}