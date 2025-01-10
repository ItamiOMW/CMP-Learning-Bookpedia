package com.itami.bookpedia.book_feature.presentation.book_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cmp_learning_bookpedia.composeapp.generated.resources.Res
import cmp_learning_bookpedia.composeapp.generated.resources.book_error
import coil3.compose.rememberAsyncImagePainter
import com.itami.bookpedia.book_feature.domain.model.Book
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

@Composable
internal fun BookListItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(30.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BookImage(
                bookTitle = book.title,
                bookImageUrl = book.imageUrl
            )
            BookInformation(
                bookTitle = book.title,
                authors = book.authors,
                avgRating = book.averageRating
            )
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun RowScope.BookImage(
    bookTitle: String,
    bookImageUrl: String,
) {
    Box(
        modifier = Modifier.height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }

        val painter = rememberAsyncImagePainter(
            model = bookImageUrl,
            onSuccess = {
                imageLoadResult = if (
                    it.painter.intrinsicSize.height > 1 &&
                    it.painter.intrinsicSize.width > 1
                ) {
                    Result.success(it.painter)
                } else {
                    Result.failure(Exception("Invalid image size"))
                }
            },
            onError = { error ->
                val throwable = error.result.throwable
                throwable.printStackTrace()
                imageLoadResult = Result.failure(throwable)
            }
        )

        when (val result = imageLoadResult) {
            null -> CircularProgressIndicator()
            else -> {
                Image(
                    painter = if (result.isSuccess) {
                        painter
                    } else {
                        painterResource(Res.drawable.book_error)
                    },
                    contentDescription = bookTitle,
                    contentScale = if (result.isSuccess) {
                        ContentScale.Crop
                    } else {
                        ContentScale.Fit
                    },
                    modifier = Modifier
                        .aspectRatio(
                            ratio = 0.65f,
                            matchHeightConstraintsFirst = true
                        )
                )
            }
        }
    }
}

@Composable
private fun RowScope.BookInformation(
    bookTitle: String,
    authors: List<String>,
    avgRating: Double?,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = bookTitle,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        authors.firstOrNull()?.let { author ->
            Text(
                text = author,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        avgRating?.let { rating ->
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${round(rating * 10 / 10.0)}", // 4.66000001 -> 4.7
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

