package com.example.bookshelf2.ui.home.bookdetails

import android.util.Log
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshelf2.model.BookDetailsResponse
import com.example.bookshelf2.model.Description
import com.example.bookshelf2.ui.components.BookListImageItem
import com.example.bookshelf2.ui.components.BookShelfScaffold
import com.example.bookshelf2.ui.home.search.DetailsUiState
import com.example.bookshelf2.ui.home.search.SearchViewModel
import com.example.bookshelf2.ui.theme.BookShelfTheme


fun <T> spatialExpressiveSpring() = spring<T>(
    dampingRatio = 0.8f,
    stiffness = 380f
)

fun <T> nonSpatialExpressiveSpring() = spring<T>(
    dampingRatio = 1f,
    stiffness = 1600f
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetails(
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    val detailsUiState by searchViewModel.detailsUiState.collectAsState()
    val coverId = searchViewModel.coverId

    BookShelfScaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bookshelf",
                        style = MaterialTheme.typography.titleLarge,
                        color = BookShelfTheme.colors.iconInteractiveInactive
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = BookShelfTheme.colors.iconInteractiveInactive
                        )
                    }
                },
                modifier = modifier,
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(
                        containerColor = BookShelfTheme.colors.uiBackground,
                        scrolledContainerColor = BookShelfTheme.colors.uiBackground
                    )
            )
        }
    ) { padding ->
        when(val state = detailsUiState) {
            is DetailsUiState.Loading -> {
                LoadingScreen()
            }

            is DetailsUiState.Error -> {
                ErrorScreen(detailsUiState = state)
            }

            is DetailsUiState.Success -> {
                val book = state.book

                BookDetailsScreen(
                    //modifier = modifier.padding(padding),
                    book = book,
                    padding = padding,
                    coverId = coverId
                )
            }
        }
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = BookShelfTheme.colors.selectedIconBorderFill)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, detailsUiState: DetailsUiState.Error) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error: ${detailsUiState.message}",
            color = MaterialTheme.colorScheme.error
        )
    }
}


@Composable
fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    book: BookDetailsResponse,
    padding: PaddingValues,
    coverId: Int?
) {
    Log.d("BookDetailsScreen", "response:\n, ${book.title}, ${book.firstPublishYear}, ${book.description?.value}, bye")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = modifier.padding(top = 60.dp))
        BookImage(
            modifier = modifier
                .padding(padding)
                .height(240.dp)
                .width(170.dp)
                .align(Alignment.CenterHorizontally)
            ,
            coverId = coverId,
            size = 'L'
        )

        Text(
            text = book.title,
            modifier = modifier
                .padding(top = 30.dp),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 30.sp,
            lineHeight = 40.sp,
        )

        Text(
            text = book.firstPublishYear.toString(),
            modifier = modifier
                .padding(top = 15.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.W500
        )

        Text(
            text = "-",
            //text = book.authors?.joinToString(", ") ?: "Unknown Author",
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
                .width(160.dp)
                .height(85.dp)
                .padding(top = 30.dp)
        ) {
            Text(
                text = "Add to my Library",
                fontWeight = FontWeight.Bold,
            )
        }

        Text(
            text = "Description",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = book.description?.value?: "No description available",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.W600,
            //textAlign = TextAlign.Justify,
            color = BookShelfTheme.colors.textPlain
        )
        
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun BookImage(
    modifier: Modifier,
    coverId: Int?,
    size: Char
) {
    Log.d("BookImage", "coverId: $coverId")
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(15.dp))
    ) {
        BookListImageItem(
            coverId = coverId,
            context = LocalContext.current,
            modifier = Modifier.fillMaxSize(),
            size = size
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun BookDetailsScreenPreview() {
    BookShelfTheme {
        BookDetailsScreen(modifier = Modifier,
            book = BookDetailsResponse(title = "title", firstPublishYear = 2023, description = Description("d", "hello worldkajfld kjs lasjdf lksjdfl ajksdlfk jasdlfskljadf l;askjdf lsdjflsa jflksdj lsdakjf lasjdfl sdjldsakjfl skdjfl aksdjfldshgdlfhlsdahgkjdshf;las djdhfg;lasjdl ;gkdjfhgl; ")),
            //book = BookDetailsResponse(title = "title", authors = listOf("author1", "author2"), description = "description", firstPublishYear = 2023),
            padding = PaddingValues(),
            coverId = 34534
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun BookDetailsScreenPreviewDark() {
    BookShelfTheme(darkTheme = true) {
        BookDetailsScreen(modifier = Modifier,
            book = BookDetailsResponse(title = "title", firstPublishYear = 2023, description = Description("d", "hello worldkajfld kjs lasjdf lksjdfl ajksdlfk jasdlfskljadf l;askjdf lsdjflsa jflksdj lsdakjf lasjdfl sdjldsakjfl skdjfl aksdjfldshgdlfhlsdahgkjdshf;las djdhfg;lasjdl ;gkdjfhgl; ")),
            //book = BookDetailsResponse(title = "title", authors = listOf("author1", "author2"), description = "description", firstPublishYear = 2023),
            padding = PaddingValues(),
            coverId = 34534
        )
    }
}




