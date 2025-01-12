package com.itami.bookpedia.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.itami.bookpedia.book_feature.presentation.SelectedBookViewModel
import com.itami.bookpedia.book_feature.presentation.book_details.BookDetailsAction
import com.itami.bookpedia.book_feature.presentation.book_details.BookDetailsScreenRoot
import com.itami.bookpedia.book_feature.presentation.book_details.BookDetailsViewModel
import com.itami.bookpedia.book_feature.presentation.book_list.BookListScreenRoot
import com.itami.bookpedia.book_feature.presentation.book_list.BookListViewModel
import com.itami.bookpedia.core.presentation.theme.defaultColorScheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

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
                composable<Graph.BooksGraph.BooksListScreen>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) { navBackStackEntry ->
                    val selectedBookViewModel = navBackStackEntry
                        .sharedKoinViewModel<SelectedBookViewModel>(navController)
                    val bookListViewModel = koinViewModel<BookListViewModel>()

                    LaunchedEffect(true) {
                        selectedBookViewModel.onSelectBook(null)
                    }

                    BookListScreenRoot(
                        viewModel = bookListViewModel,
                        onNavigateToBookDetails = { book ->
                            selectedBookViewModel.onSelectBook(book)
                            navController.navigate(Graph.BooksGraph.BookDetailsScreen(book.id))
                        }
                    )
                }
                composable<Graph.BooksGraph.BookDetailsScreen>(
                    enterTransition = { slideInHorizontally { it } },
                    exitTransition = { slideOutHorizontally { it } }
                ) { navBackStackEntry ->
                    val selectedBookViewModel = navBackStackEntry
                        .sharedKoinViewModel<SelectedBookViewModel>(navController)
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                    val bookDetailsViewModel = koinViewModel<BookDetailsViewModel>()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.let {
                            bookDetailsViewModel.onAction(BookDetailsAction.OnSelectedBookChange(it))
                        }
                    }

                    BookDetailsScreenRoot(
                        viewModel = bookDetailsViewModel,
                        onNavigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(viewModelStoreOwner = parentEntry)
}