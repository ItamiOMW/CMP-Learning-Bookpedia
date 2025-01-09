package com.itami.bookpedia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.itami.bookpedia.book_feature.presentation.book_list.BookListScreenRoute
import com.itami.bookpedia.book_feature.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = Color(0xFF0B405E),
            primaryContainer = Color(0xFF9AD9FF),
            onPrimary = Color.Black,
            secondary = Color(0xFFFFBD64),
            background = Color(0xFFF7F7F7),
            onBackground = Color.Black,
            surface = Color.White,
            onSurface = Color.Black,
            onSurfaceVariant = Color.Gray,
            outline = Color(0xff41678c),
        )
    ) {
        BookListScreenRoute(
            viewModel = BookListViewModel(),
            onNavigateToBookDetails = {}
        )
    }
}