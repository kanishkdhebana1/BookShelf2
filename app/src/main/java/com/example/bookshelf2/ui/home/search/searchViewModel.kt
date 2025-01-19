package com.example.bookshelf2.ui.home.search

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf2.BookshelfApplication
import com.example.bookshelf2.data.BookshelfRepository
import com.example.bookshelf2.model.BookDetailsResponse
import com.example.bookshelf2.model.BookItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID


sealed class SearchUiState {
    data object Loading : SearchUiState()
    data class Success(val books: List<BookItem>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

sealed class DetailsUiState {
    data object Loading : DetailsUiState()
    data class Success(val book: BookDetailsResponse) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}



class SearchViewModel(
    private val bookshelfRepository: BookshelfRepository
): ViewModel() {

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState

    private val _detailsUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val detailsUiState: StateFlow<DetailsUiState> = _detailsUiState

    private var _searchTerm = mutableStateOf("")
    private var _bookKey = mutableStateOf("")
    private var _coverId = mutableIntStateOf(0)
    val coverId: Int
        get() = _coverId.intValue


    private var lastSearchTime = System.currentTimeMillis()

     fun updateSearchTerm(newSearchTerm: String) {
        _searchTerm.value = newSearchTerm
         debounceSearch(newSearchTerm)
    }

    private  fun debounceSearch(query: String) {
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastSearchTime < 300) {
            return  // Avoid making the request if the last request was too recent
        }

        lastSearchTime = currentTime
        searchBooks(query)
    }

    fun updateBookKey(newBookKey: String, newCoverId: Int) {
        _bookKey.value = newBookKey
        _coverId.intValue = newCoverId
        searchBookDetails(_bookKey.value)
    }


    private  fun searchBooks(query: String) {
        _searchUiState.value = SearchUiState.Loading
        Log.d("SearchUiState", "Emitting Loading state")

        viewModelScope.launch {
            try {
                val response = bookshelfRepository.searchBooks(query)

                val books: List<BookItem> = response.docs.map { bookItem ->
                    val uniqueKey = UUID.randomUUID().toString().hashCode()

                    BookItem(
                        coverId = bookItem.coverId,
                        id = uniqueKey,
                        title = bookItem.title,
                        authors = bookItem.authors,
                        firstPublishYear = bookItem.firstPublishYear,
                        key = bookItem.key
                    )
                }

                _searchUiState.value = SearchUiState.Success(books)
                Log.d("HOLLLLAAAA", "Ui State: {${searchUiState.value}}")
            } catch (exception: Exception) {
                // Handle error if necessary
                _searchUiState.value = SearchUiState.Error(exception.message ?: "Unknown error")
                Log.d("HOLLLLAAAA", "Error: ${exception.message}")
            }
        }
    }


    private  fun searchBookDetails(key: String) {
        _detailsUiState.value = DetailsUiState.Loading
        val extractedKey = key.substringAfterLast("/")

        viewModelScope.launch {
            try {
                val response = bookshelfRepository.searchBookDetails(extractedKey)
                _detailsUiState.value = DetailsUiState.Success(response)

                Log.d("SearchViewModel", response.toString())

            } catch (exception: Exception) {
                _detailsUiState.value = DetailsUiState.Error(exception.message ?: "Unknown error")
                Log.e("SearchViewModel", "Error fetching book details", exception)
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]

                if (application is BookshelfApplication) {
                    val bookshelfRepository = application.container.bookshelfRepository
                    SearchViewModel(bookshelfRepository = bookshelfRepository)
                } else {
                    throw IllegalStateException("SearchViewModel not initialized properly")
                }
            }
        }


    }
}