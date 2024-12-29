package com.example.bookshelf2.ui.home.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf2.ui.components.BookCardWide
import com.example.bookshelf2.ui.home.BookshelfUiState
import com.example.bookshelf2.ui.home.HomeViewModel

@Composable
fun Result(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModel.Factory)

    val searchTerm = "Harry Potter And The Deathly Hallows"

    Button(
        onClick = {
            homeViewModel.updateSearchTerm(searchTerm)
        },
        modifier = modifier.padding(16.dp)
    ) {

    }

//    LaunchedEffect(searchTerm) {
//        homeViewModel.updateSearchTerm(searchTerm)
//    }

    val books = when (val homeUiState = homeViewModel.bookshelfUiState) {
        is BookshelfUiState.Success -> homeUiState.books
        else -> emptyList()
    }

    Text(text = "", modifier = Modifier.height(8.dp))

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
            top = contentPadding.calculateTopPadding(),
            end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
            bottom = contentPadding.calculateBottomPadding() + 16.dp // Add extra bottom padding here
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp),
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