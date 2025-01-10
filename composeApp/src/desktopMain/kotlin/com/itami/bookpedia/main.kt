package com.itami.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.itami.bookpedia.app.App
import com.itami.bookpedia.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP-Learning-Bookpedia",
        ) {
            App()
        }
    }
}