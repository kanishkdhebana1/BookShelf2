package com.example.bookshelf2.ui.home.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf2.model.BookItem
import com.example.bookshelf2.ui.components.BookCardCompact
import com.example.bookshelf2.ui.home.BookshelfUiState
import com.example.bookshelf2.ui.home.HomeViewModel
import com.example.bookshelf2.ui.theme.BookShelfTheme

@Composable
fun Feed(
    //onBookClick: (Long, String) -> Unit,
    modifier: Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModel.Factory)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FeedBarSection(
            title = "New Arrivals",
            uiState = homeViewModel.newArrivalsUiState
        )
        FeedBarSection(
            title = "Top Rated",
            uiState = homeViewModel.topRatedUiState
        )
        FeedBarSection(
            title = "Trending",
            uiState = homeViewModel.trendingUiState
        )
    }

}


@Composable
fun FeedBarSection(
    title: String,
    uiState: BookshelfUiState,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is BookshelfUiState.Loading -> {
            Text(
                text = "Loading $title...",
                modifier = modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        is BookshelfUiState.Error -> {
            Text(
                text = "Failed to load $title",
                modifier = modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }

        is BookshelfUiState.Success -> {
            FeedBar(
                title = title,
                books = uiState.books,
                modifier = modifier,

            )
        }
    }
}

@Composable
fun FeedBar(
    modifier: Modifier = Modifier,
    title: String,
    books: List<BookItem>
) {
    val listState = rememberLazyListState() // State to monitor the scroll position

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(270.dp)
            .background(color = BookShelfTheme.colors.uiBackground)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = BookShelfTheme.colors.textPlain,
                fontWeight = FontWeight.W700,
                modifier = modifier.padding(bottom = 10.dp)
            )

            Box {
                Row {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        items (
                            items = books,
                            key = { book ->
                                book.id
                            }
                        ) { book ->
                            BookCardCompact(book = book)
                        }
                    }

                    IconButton(
                        onClick = { /* Add your action here */ },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Go Forward",
                            tint = BookShelfTheme.colors.iconInteractive
                        )
                    }
                }
            }
        }
    }
}

