package com.itami.bookpedia.book_feature.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_learning_bookpedia.composeapp.generated.resources.Res
import cmp_learning_bookpedia.composeapp.generated.resources.favorites
import cmp_learning_bookpedia.composeapp.generated.resources.no_favorite_books
import cmp_learning_bookpedia.composeapp.generated.resources.no_search_results
import cmp_learning_bookpedia.composeapp.generated.resources.search_results
import com.itami.bookpedia.book_feature.domain.model.Book
import com.itami.bookpedia.book_feature.presentation.book_list.components.BookList
import com.itami.bookpedia.book_feature.presentation.book_list.components.BookSearchBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoute(
    viewModel: BookListViewModel = koinViewModel(),
    onNavigateToBookDetails: (Book) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is BookListEvent.OnNavigateToBookDetails -> onNavigateToBookDetails(event.book)
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchResultsListState = rememberLazyListState()

    val favoritesListState = rememberLazyListState()

    val pagerState = rememberPagerState { 2 }

    LaunchedEffect(state.searchQuery) {
        searchResultsListState.animateScrollToItem(index = 0)
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = { query ->
                onAction(BookListAction.OnSearchQueryChange(query))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectedTabIndex]),
                        )
                    },
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(0))
                        },
                        selectedContentColor = MaterialTheme.colorScheme.secondary,
                        unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                        text = {
                            Text(
                                text = stringResource(Res.string.search_results),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    )
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(1))
                        },
                        selectedContentColor = MaterialTheme.colorScheme.secondary,
                        unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                        text = {
                            Text(
                                text = stringResource(Res.string.favorites),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    )
                }
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    state = pagerState,
                ) { page ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (page) {
                            0 -> {
                                when {
                                    state.isLoading -> {
                                        CircularProgressIndicator()
                                    }

                                    state.errorMessage != null -> {
                                        Text(
                                            text = state.errorMessage.asString(),
                                            style = MaterialTheme.typography.bodyLarge,
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }

                                    state.searchResults.isNotEmpty() -> {
                                        Text(
                                            text = stringResource(Res.string.no_search_results),
                                            style = MaterialTheme.typography.bodyLarge,
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }

                                    else -> {
                                        BookList(
                                            modifier = Modifier.fillMaxWidth(),
                                            books = state.searchResults,
                                            listState = searchResultsListState,
                                            onBookClick = { book ->
                                                onAction(BookListAction.OnBookClick(book))
                                            }
                                        )
                                    }
                                }
                            }

                            1 -> {
                                if (state.favoriteBooks.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favorite_books),
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                                    )
                                } else {
                                    BookList(
                                        modifier = Modifier.fillMaxWidth(),
                                        books = state.favoriteBooks,
                                        listState = favoritesListState,
                                        onBookClick = { book ->
                                            onAction(BookListAction.OnBookClick(book))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}