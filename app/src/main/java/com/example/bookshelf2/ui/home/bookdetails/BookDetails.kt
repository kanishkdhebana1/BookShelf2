package com.example.bookshelf2.ui.home.bookdetails

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.bookshelf2.model.BookItem
import com.example.bookshelf2.ui.components.BookListImageItem
import com.example.bookshelf2.ui.components.BookShelfScaffold
import com.example.bookshelf2.ui.navigation.MainDestinations
import com.example.bookshelf2.ui.theme.BookShelfColors
import com.example.bookshelf2.ui.theme.BookShelfTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetails(
    modifier: Modifier = Modifier,
    book: BookItem
) {
    BookShelfScaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bookshelf",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.DarkGray
                        )
                    }
                },
                modifier = modifier,
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(
                        containerColor = Color.White,
                        scrolledContainerColor = Color.White
                    )
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Spacer(modifier = modifier.padding(top = 60.dp))
            BookImage(
                modifier = modifier
                    .padding(padding)
                    .height(230.dp)
                    .width(170.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                book = book
            )

            Text(
                text = book.title,
                modifier = modifier
                    .padding(top = 30.dp),
                style = MaterialTheme.typography.headlineLarge,
            )

            Text(
                text = book.firstPublishYear.toString(),
                modifier = modifier
                    .padding(top = 15.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.W500
            )

            Text(
                text = book.authors?.joinToString(", ") ?: "Unknown Author",
                modifier = modifier
                    .padding(top = 15.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W400
            )

            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.iconButtonColors()
                    .copy(
                        containerColor = BookShelfTheme.colors.selectedIconBorderFill,
                        contentColor = Color.White
                    ),
                modifier = modifier
                    .width(150.dp)
                    .padding(top = 20.dp)
            ) {
                Text(text = "Add to my Library")
            }

            Text(
                text = "Description",
                modifier = modifier
                    .padding(top = 25.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BookImage(
    modifier: Modifier,
    book: BookItem
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .border(width = 400.dp, color = Color.Black),
    ) {
        BookListImageItem(book = book, context = LocalContext.current)
    }
}

@Preview(backgroundColor = 0xde003455)
@Composable
fun BookDetailsPreview() {
    BookShelfTheme {
        BookDetails(modifier = Modifier, book = BookItem(1, 1, "title", listOf("author"), listOf(2020)))
    }
}


