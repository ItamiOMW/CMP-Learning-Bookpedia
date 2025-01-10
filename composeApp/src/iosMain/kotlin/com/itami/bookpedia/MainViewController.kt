package com.itami.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.itami.bookpedia.app.App
import com.itami.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }