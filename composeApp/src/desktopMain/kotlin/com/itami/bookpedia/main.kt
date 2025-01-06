package com.itami.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CMP-Learning-Bookpedia",
    ) {
        App()
    }
}