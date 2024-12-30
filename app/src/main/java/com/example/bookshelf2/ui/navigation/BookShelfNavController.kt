package com.example.bookshelf2.ui.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


object MainDestinations {
    const val HOME_ROUTE = "Home"
    const val BOOK_DETAIL_ROUTE = "Book"
    const val BOOK_ID_KEY = "BookId"
    const val ORIGIN = "Origin"
    const val RESULT = "Result"
}

@Composable
fun rememberBookShelfNavController(
    navController: NavHostController = rememberNavController()
): BookShelfNavController = remember(navController) {
    BookShelfNavController(navController)
}


@Stable
class BookShelfNavController(
    val navController: NavHostController,
) {
    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        Log.d("BookShelfNavController", "navigateToBottomBarRoute: $route")
        if (route != navController.currentDestination?.route) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                // Pop up backstack to the first destination and save state. This makes going back
                // to the start destination when pressing back in any other bottom tab.
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToBookDetail(bookId: Long, origin: String, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.BOOK_DETAIL_ROUTE}/$bookId?origin=$origin")
        }
    }

    fun navigateToResult(query: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            val encodedQuery = Uri.encode(query) // Encode the query
            Log.d("Navigation", "Navigating to result: ${MainDestinations.RESULT}/$encodedQuery")
            navController.navigate("${MainDestinations.RESULT}/$encodedQuery")
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
