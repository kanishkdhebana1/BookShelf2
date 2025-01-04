package com.example.bookshelf2.ui.components

import android.content.Context
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
                book = book,
                modifier = modifier
                    .fillMaxWidth()
                    .height(150.dp),
                context = context
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
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        onClick = { /*TODO*/ },
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
                book = book,
                context = context,
                modifier = modifier
                    .width(95.dp)
                    .fillMaxHeight()
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
                    color = Color.Black,
                    fontSize = 20.sp
                )

                Spacer(modifier = modifier.weight(1f))



                Text(
                    text = book.firstPublishYear?.first().toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray,
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
    book: BookItem,
    modifier: Modifier = Modifier,
    context: Context
) {

    val thumbnailUrl = "https://covers.openlibrary.org/b/id/${book.coverId}-M.jpg"

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(thumbnailUrl)
                .crossfade(true)
                .build(),
            contentDescription = book.title,
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth,
            placeholder = painterResource(id = R.drawable.loading),
            error = painterResource(id = R.drawable.placeholder_book),
            modifier = Modifier
                .matchParentSize()
                .background(Color.White)

        )
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xde000000)
@Composable
fun BookCardCompactPreview() {
    BookShelfTheme {
        Row {
            BookCardCompact(
                book = BookItem(1, 1, "Big little title", listOf("author"), listOf(2020)),
            )
            Spacer(modifier = Modifier.weight(1f))
            BookCardCompact(
                book = BookItem(1, 1, "Big little title", listOf("author"), listOf(2020)),
            )

            Spacer(modifier = Modifier.weight(1f))

            BookCardCompact(
                book = BookItem(1, 1, "Big little title", listOf("author"), listOf(2020)),
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookCardWidePreview() {
    BookShelfTheme {
        BookCardWide(book = BookItem(1, 1, "title", listOf("author"), listOf(2020)))
    }
}