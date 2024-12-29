package com.example.bookshelf2.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf2.ui.components.BookCardWide

@Composable
fun Feed(
    onBookClick: (Long, String) -> Unit,
    modifier: Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
    ) {

    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModel.Factory)

    val searchTerm = "Harry Potter And The Deathly Hallows"

    Button(
        onClick = {
            homeViewModel.updateSearchTerm(searchTerm)
        },
        modifier = modifier.padding(100.dp)
    ) {

    }

//    LaunchedEffect(searchTerm) {
//        homeViewModel.updateSearchTerm(searchTerm)
//    }

    val books = when (val homeUiState = homeViewModel.bookshelfUiState) {
        is BookshelfUiState.Success -> homeUiState.books
        else -> emptyList()
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
            top = contentPadding.calculateTopPadding(),
            end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
            bottom = contentPadding.calculateBottomPadding() + 16.dp // Extra space at the bottom
        ),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(
            items = books,
            key = {
                book ->
                book.id
            }
        ) { book ->
            Column {
                BookCardWide(book = book)
            }
        }
    }
}
