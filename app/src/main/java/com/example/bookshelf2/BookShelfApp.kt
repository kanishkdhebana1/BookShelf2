@file:OptIn(
    ExperimentalSharedTransitionApi::class
)

package com.example.bookshelf2

import androidx.annotation.ColorRes
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.bookshelf2.ui.bookdetail.BookDetail
import com.example.bookshelf2.ui.bookdetail.nonSpatialExpressiveSpring
import com.example.bookshelf2.ui.bookdetail.spatialExpressiveSpring
import com.example.bookshelf2.ui.components.BookShelfScaffold
import com.example.bookshelf2.ui.components.BookShelfSnackbar
import com.example.bookshelf2.ui.components.rememberBookShelfScaffoldState
import com.example.bookshelf2.ui.home.BookShelfBottomBar
import com.example.bookshelf2.ui.home.HomeSections
import com.example.bookshelf2.ui.home.addHomeGraph
import com.example.bookshelf2.ui.home.composableWithCompositionLocal
import com.example.bookshelf2.ui.navigation.MainDestinations
import com.example.bookshelf2.ui.navigation.rememberBookShelfNavController
import com.example.bookshelf2.ui.theme.BookShelfTheme

@Composable
fun BookShelfApp() {
    BookShelfTheme {
        val bookShelfNavController = rememberBookShelfNavController()

        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this
            ) {
                NavHost(
                    navController = bookShelfNavController.navController,
                    startDestination = MainDestinations.HOME_ROUTE
                ) {
                    composableWithCompositionLocal(
                        route = MainDestinations.HOME_ROUTE
                    ) {
                        MainContainer(
                            modifier = Modifier,
                            onBookSelected = bookShelfNavController::navigateToBookDetail
                        )
                    }
                    
                    composableWithCompositionLocal(
                        route = "${MainDestinations.BOOK_DETAIL_ROUTE}/" +
                                "{${MainDestinations.BOOK_ID_KEY}}" +
                                "?origin={${MainDestinations.ORIGIN}}",

                        arguments = listOf(
                            navArgument(MainDestinations.BOOK_ID_KEY) {
                                type = NavType.LongType
                            }
                        ),
                    ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val bookId = arguments.getLong(MainDestinations.BOOK_ID_KEY)
                        val origin = arguments.getString(MainDestinations.ORIGIN)

                        BookDetail(
                            bookId = bookId,
                            origin = origin ?: "",
                            upPress = bookShelfNavController::upPress
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    onBookSelected: (Long, String, NavBackStackEntry) -> Unit
) {
    val scaffoldState = rememberBookShelfScaffoldState()
    val nestedNavController = rememberBookShelfNavController()
    val navBackStackEntry by nestedNavController.navController.currentBackStackEntryAsState()
    val currentRoute =  navBackStackEntry?.destination?.route
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No SharedElementScope found")

    BookShelfScaffold(
        topBar = {
            CustomTopBar(
                currentRoute = currentRoute ?: HomeSections.FEED.route
            )
        }
        ,
        bottomBar = {
            with(animatedVisibilityScope) {
                with(sharedTransitionScope) {
                    BookShelfBottomBar(
                        tabs = HomeSections.entries.toTypedArray(),
                        currentRoute = currentRoute ?: HomeSections.FEED.route,
                        navigateToRoute = nestedNavController::navigateToBottomBarRoute,
                        modifier = Modifier
                            .renderInSharedTransitionScopeOverlay(
                                zIndexInOverlay = 1f,
                            )
                            .animateEnterExit(
                                enter = fadeIn(nonSpatialExpressiveSpring()) + slideInVertically(
                                    spatialExpressiveSpring()
                                ) {
                                    it
                                },
                                exit = fadeOut(nonSpatialExpressiveSpring()) + slideOutVertically(
                                    spatialExpressiveSpring()
                                ) {
                                    it
                                }
                            )
                    )
                }
            }
        },
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = it,
                modifier = Modifier.systemBarsPadding(),
                snackbar = { snackbarData -> BookShelfSnackbar(snackbarData) }
            )
        },
        snackBarHostState = scaffoldState.snackBarHostState
    ) { padding ->
        NavHost(
            navController = nestedNavController.navController,
            startDestination = HomeSections.FEED.route
        ) {
            addHomeGraph(
                onBookSelected = onBookSelected,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(currentRoute: String, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    HomeSections.FEED.route -> "Home"
                    HomeSections.SEARCH.route -> "Search"
                    HomeSections.LIBRARY.route -> "Favorites"
                    HomeSections.PROFILE.route -> "Settings"
                    else -> "BookShelf"
                },
                style = MaterialTheme.typography.titleLarge,
                color = BookShelfTheme.colors.selectedIconBorderFill
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (currentRoute != HomeSections.FEED.route) {
                IconButton(onClick = { /* Handle navigation or back press */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            if (currentRoute == HomeSections.SEARCH.route) {
                IconButton(onClick = { /* Handle search action */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors()
            .copy(
                containerColor = Color.White,
                scrolledContainerColor = Color.White
            )
    )
}



val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }


@Preview(showBackground = true)
@Composable
fun BookShelfAppPreview() {
    BookShelfTheme {
        BookShelfApp()
    }
}