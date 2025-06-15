package com.np.webapiapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.np.webapiapp.data.model.Dataset
import com.np.webapiapp.data.repository.DatasetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DatasetUiState(
    val datasets: List<Dataset> = emptyList(),
    val favoriteDatasets: List<Dataset> = emptyList(),
    val searchResults: List<Dataset> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

class DatasetViewModel(private val repository: DatasetRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(DatasetUiState())
    val uiState: StateFlow<DatasetUiState> = _uiState.asStateFlow()

    init {
        loadDatasets()
        loadFavoriteDatasets()
    }

    private fun loadDatasets() {
        viewModelScope.launch {
            repository.getAllDatasets().collect { datasets ->
                _uiState.update { it.copy(datasets = datasets) }
            }
        }
    }

    private fun loadFavoriteDatasets() {
        viewModelScope.launch {
            repository.getFavoriteDatasets().collect { favorites ->
                _uiState.update { it.copy(favoriteDatasets = favorites) }
            }
        }
    }

    fun refreshDatasets() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repository.refreshDatasets()
                _uiState.update { it.copy(isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun searchDatasets(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(searchQuery = query, isLoading = true) }
            try {
                val results = repository.searchDatasets(query)
                _uiState.update { it.copy(searchResults = results, isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun toggleFavorite(datasetId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(datasetId, isFavorite)
        }
    }

    class Factory(private val repository: DatasetRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DatasetViewModel::class.java)) {
                return DatasetViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
} 