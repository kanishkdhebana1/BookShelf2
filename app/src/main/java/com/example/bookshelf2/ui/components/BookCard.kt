package com.example.bookshelf2.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf2.R
import com.example.bookshelf2.model.BookItem
import com.example.bookshelf2.ui.theme.BookShelfTheme

@Composable
fun BookCardCompact(
    book: BookItem,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coverId = book.coverId

    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
            .padding(8.dp)
            .height(190.dp)
            .width(110.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors()
            .copy(
                containerColor = Color.Gray,
            )
    ) {
        Column {
            BookListImageItem(
                coverId = coverId,
                modifier = modifier
                    .fillMaxWidth()
                    .height(150.dp),
                context = context,
                size = 'M'
            )

            Text(
                text = book.title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                fontSize = 17.sp,
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun BookCardWide(
    book: BookItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val coverId = book.coverId

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(start = 9.dp, end = 9.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors()
            .copy(
                containerColor = BookShelfTheme.colors.cardColor,
            )
    ) {
        Row(
            modifier = modifier.fillMaxSize()
        ) {
            BookListImageItem(
                coverId = coverId,
                modifier = modifier
                    .width(95.dp)
                    .fillMaxHeight(),
                context = context,
                size = 'M'
            )

            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(8.dp)

            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = BookShelfTheme.colors.textPlain,
                    fontSize = 20.sp
                )

                Spacer(modifier = modifier.weight(1f))



                Text(
                    text = book.firstPublishYear?.first().toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = BookShelfTheme.colors.textPlain,
                    fontSize = 15.sp,
                    modifier = modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = modifier.weight(1f))

                Text(
                    text = book.authors?.joinToString(", ") ?: "Unknown Author",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    fontSize = 15.sp,
                    modifier = modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}


@Composable
fun BookListImageItem(
    coverId: Int?,
    modifier: Modifier = Modifier,
    context: Context,
    size: Char
) {
    val thumbnailUrl = "https://covers.openlibrary.org/b/id/${coverId}-${size}.jpg"
    Log.d("BookListImageItem", coverId.toString())
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(thumbnailUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            alignment = Alignment.Center,
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(id = R.drawable.loading),
            error = painterResource(id = R.drawable.placeholder_book),
            modifier = Modifier
                .matchParentSize()
                .background(Color.White)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BookCardWidePreview() {
    BookShelfTheme {
        BookCardWide(book = BookItem(1, 1, "title", key = "", listOf("author"), listOf(2020)), onClick = {  })
    }
}

@Preview(showBackground = true)
@Composable
fun BookCardWidePreviewDark() {
    BookShelfTheme(darkTheme = true) {
        BookCardWide(book = BookItem(1, 1, "title", key = "", listOf("author"), listOf(2020)), onClick = {  })
    }
}


@Preview(showBackground = true)
@Composable
fun BookCardCompactPreview() {
    BookShelfTheme {
        Row {
            BookCardCompact(
                book = BookItem(1, 1, "Big little title", key = "",listOf("author"), listOf(2020)),
            )
            Spacer(modifier = Modifier.weight(1f))
            BookCardCompact(
                book = BookItem(1, 1, "Big little title", key = "", listOf("author"), listOf(2020)),
            )

            Spacer(modifier = Modifier.weight(1f))

            BookCardCompact(
                book = BookItem(1, 1, "Big little title", key = "", listOf("author"), listOf(2020))
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun BookCardCompactPreviewDark() {
    BookShelfTheme(darkTheme = true) {
        Row {
            BookCardCompact(
                book = BookItem(1, 1, "Big little title", key = "",listOf("author"), listOf(2020)),
            )
            Spacer(modifier = Modifier.weight(1f))
            BookCardCompact(
                book = BookItem(1, 1, "Big little title", key = "", listOf("author"), listOf(2020)),
            )

            Spacer(modifier = Modifier.weight(1f))

            BookCardCompact(
                book = BookItem(1, 1, "Big little title", key = "", listOf("author"), listOf(2020))
            )
        }

    }
}
