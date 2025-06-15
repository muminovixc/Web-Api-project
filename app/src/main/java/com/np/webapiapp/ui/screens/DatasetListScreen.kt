package com.np.webapiapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.np.webapiapp.data.model.Dataset
import com.np.webapiapp.ui.components.DatasetItem
import com.np.webapiapp.ui.viewmodel.DatasetUiState
import com.np.webapiapp.ui.viewmodel.DatasetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatasetListScreen(
    viewModel: DatasetViewModel,
    onDatasetClick: (Dataset) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showSearchBar by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Open Data Portal BiH") },
            actions = {
                IconButton(onClick = { showSearchBar = !showSearchBar }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

        if (showSearchBar) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { 
                    searchQuery = it
                    viewModel.searchDatasets(it)
                },
                onSearch = { viewModel.searchDatasets(searchQuery) },
                active = true,
                onActiveChange = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                // Search suggestions could be added here
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            DatasetList(
                uiState = uiState,
                onDatasetClick = onDatasetClick,
                onFavoriteClick = { dataset, isFavorite ->
                    viewModel.toggleFavorite(dataset.id, isFavorite)
                }
            )
        }
    }
}

@Composable
private fun DatasetList(
    uiState: DatasetUiState,
    onDatasetClick: (Dataset) -> Unit,
    onFavoriteClick: (Dataset, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val datasets = if (uiState.searchQuery.isNotEmpty()) {
        uiState.searchResults
    } else {
        uiState.datasets
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(datasets) { dataset ->
            val isFavorite = uiState.favoriteDatasets.any { it.id == dataset.id }
            DatasetItem(
                dataset = dataset,
                isFavorite = isFavorite,
                onItemClick = onDatasetClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
} 