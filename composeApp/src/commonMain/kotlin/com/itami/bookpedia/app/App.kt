package com.itami.bookpedia.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.itami.bookpedia.book_feature.presentation.book_list.BookListScreenRoute
import com.itami.bookpedia.core.presentation.theme.defaultColorScheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = defaultColorScheme
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Graph.BooksGraph
        ) {
            navigation<Graph.BooksGraph>(
                startDestination = Graph.BooksGraph.BooksListScreen,
            ) {
                composable<Graph.BooksGraph.BooksListScreen> { _ ->
                    BookListScreenRoute(
                        onNavigateToBookDetails = { book ->
                            navController.navigate(Graph.BooksGraph.BookDetailsScreen(book.id))
                        }
                    )
                }
                composable<Graph.BooksGraph.BookDetailsScreen> { navBackStackEntry ->

                }
            }
        }
    }
}