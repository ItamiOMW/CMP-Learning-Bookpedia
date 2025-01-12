package com.itami.bookpedia.book_feature.presentation.book_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_learning_bookpedia.composeapp.generated.resources.Res
import cmp_learning_bookpedia.composeapp.generated.resources.description_unavailable
import cmp_learning_bookpedia.composeapp.generated.resources.languages
import cmp_learning_bookpedia.composeapp.generated.resources.pages
import cmp_learning_bookpedia.composeapp.generated.resources.rating
import cmp_learning_bookpedia.composeapp.generated.resources.synopsis
import com.itami.bookpedia.book_feature.presentation.book_details.components.BlurredImageBackground
import com.itami.bookpedia.book_feature.presentation.book_details.components.BookChip
import com.itami.bookpedia.book_feature.presentation.book_details.components.ChipSize
import com.itami.bookpedia.book_feature.presentation.book_details.components.TitledContent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.round

@Composable
fun BookDetailsScreenRoot(
    onNavigateBack: () -> Unit,
    viewModel: BookDetailsViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is BookDetailsEvent.NavigateBack -> onNavigateBack()
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    BookDetailsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BookDetailsScreen(
    state: BookDetailsState,
    onAction: (BookDetailsAction) -> Unit,
) {
    BlurredImageBackground(
        imageUrl = state.book?.imageUrl,
        isFavorite = state.isFavorite,
        onFavoriteClick = {
            onAction(BookDetailsAction.OnFavoriteClick)
        },
        onBackClick = {
            onAction(BookDetailsAction.OnBackClick)
        },
        modifier = Modifier.fillMaxSize(),
        content = {
            if (state.book != null) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp,
                            horizontal = 24.dp
                        )
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.book.title,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = state.book.authors.joinToString(),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        state.book.averageRating?.let { rating ->
                            TitledContent(
                                title = stringResource(Res.string.rating),
                            ) {
                                BookChip {
                                    Text(
                                        text = "${round(rating * 10 / 10.0)}",
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                        state.book.numPages?.let { numPages ->
                            TitledContent(
                                title = stringResource(Res.string.pages),
                            ) {
                                BookChip {
                                    Text(
                                        text = "$numPages",
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }
                            }
                        }
                    }
                    if (state.book.languages.isNotEmpty()) {
                        TitledContent(
                            title = stringResource(Res.string.languages),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            FlowRow(
                                modifier = Modifier.wrapContentSize(Alignment.Center),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                state.book.languages.forEach { lang ->
                                    BookChip(
                                        modifier = Modifier.padding(2.dp),
                                        size = ChipSize.SMALL
                                    ) {
                                        Text(
                                            text = lang.uppercase(),
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Text(
                        text = stringResource(Res.string.synopsis),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .fillMaxWidth()
                            .padding(
                                top = 24.dp,
                                bottom = 8.dp
                            )
                    )
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(vertical = 24.dp)
                        )
                    } else {
                        Text(
                            text = if (state.book.description.isNullOrBlank()) {
                                stringResource(Res.string.description_unavailable)
                            } else {
                                state.book.description
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify,
                            color = if (state.book.description.isNullOrBlank()) {
                                MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    )
}