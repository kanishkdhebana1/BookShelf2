package com.example.bookshelf2.ui.home.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.bookshelf2.ui.theme.BookShelfTheme
import com.example.bookshelf2.ui.theme.Shapes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Search(
    modifier: Modifier,
    onSearch: (String) -> Unit,
    searchViewModel: SearchViewModel,
) {
    val searchUiState by searchViewModel.searchUiState.collectAsState()

    var query by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("All") }
    var selectedSortBy by remember { mutableStateOf("Most Relevant") }
    var selectedFileType by remember { mutableStateOf("All") }

    val typeOptions = listOf("All", "Books", "Articles", "Journals")
    val sortByOptions = listOf("Most Relevant", "Newest First", "Oldest First")
    val fileTypeOptions = listOf("All", "PDF", "ePub", "Word")

    LaunchedEffect(searchUiState) {
        // This ensures recomposition happens when the UI state changes (for example after search)
        if (searchUiState is SearchUiState.Success || searchUiState is SearchUiState.Error) {
            // Perform any actions on successful state or error state
            Log.d("Search", "Search Result State: $searchUiState")
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(
                    onClick = {
                        searchViewModel.updateSearchTerm(query)
                        onSearch(query)
                    }
                ) {
                    Icon(
                        Icons.Default.Search, contentDescription = "Search Icon",
                        tint = BookShelfTheme.colors.selectedIconBorderFill
                    )
                }
            },
            shape = Shapes.small,
            colors = TextFieldDefaults.colors()
                .copy(
                    focusedIndicatorColor = Color.DarkGray,
                    unfocusedIndicatorColor = BookShelfTheme.colors.border,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    cursorColor = BookShelfTheme.colors.border,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.DarkGray
                ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    searchViewModel.updateSearchTerm(query)
                    onSearch(query)
                }
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownSelector(
            label = "Type",
            options = typeOptions,
            selectedOption = selectedType,
            onOptionSelected = { selectedType = it },
            boxWeight = 2.5f
        )

        Spacer(modifier = Modifier.height(20.dp))

        DropdownSelector(
            label = "Sort by",
            options = sortByOptions,
            selectedOption = selectedSortBy,
            onOptionSelected = { selectedSortBy = it },
            boxWeight = 2f
        )

        Spacer(modifier = Modifier.height(20.dp))

        DropdownSelector(
            label = "File type",
            options = fileTypeOptions,
            selectedOption = selectedFileType,
            onOptionSelected = { selectedFileType = it },
            boxWeight = 1.5f
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    boxWeight: Float
) {

    var expanded by remember { mutableStateOf(false) }

    Row {
        Box (modifier = Modifier.weight(boxWeight)) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(type = MenuAnchorType.PrimaryEditable),
                    value = selectedOption,
                    label = { Text(text = label) },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    onValueChange = {},
                    shape = Shapes.small,
                    colors = OutlinedTextFieldDefaults.colors()
                        .copy(
                            focusedLabelColor = BookShelfTheme.colors.selectedIconBorderFill,
                            unfocusedLabelColor = BookShelfTheme.colors.selectedIconBorderFill,
                            focusedTrailingIconColor = BookShelfTheme.colors.border,
                            unfocusedTrailingIconColor = BookShelfTheme.colors.border,
                            focusedIndicatorColor = Color.DarkGray,
                            unfocusedIndicatorColor = BookShelfTheme.colors.border,
                        ),
                    textStyle = TextStyle(color = Color.Black)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = !expanded },
                    containerColor = Color.White,
                    shape = Shapes.small,
                    border = BorderStroke(width = 1.dp, color = Color.Gray),
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    option,
                                    color = Color.DarkGray
                            )},
                            onClick = {
                                onOptionSelected(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

