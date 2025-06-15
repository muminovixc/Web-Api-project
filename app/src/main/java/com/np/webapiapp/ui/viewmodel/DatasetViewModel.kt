package com.np.webapiapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.np.webapiapp.data.model.Dataset
import com.np.webapiapp.data.repository.DatasetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DatasetUiState(
    val datasets: List<Dataset> = emptyList(),
    val favoriteDatasets: List<Dataset> = emptyList(),
    val selectedDataset: Dataset? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class DatasetViewModel @Inject constructor(
    private val repository: DatasetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DatasetUiState())
    val uiState: StateFlow<DatasetUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.getAllDatasets(),
                repository.getFavoriteDatasets()
            ) { datasets, favorites ->
                _uiState.update { currentState ->
                    currentState.copy(
                        datasets = datasets,
                        favoriteDatasets = favorites
                    )
                }
            }.collect()
        }
    }

    fun searchDatasets(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, searchQuery = query) }
            try {
                repository.refreshDatasets(query)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun loadDatasetDetails(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val dataset = repository.getDatasetDetails(id)
                _uiState.update { it.copy(selectedDataset = dataset) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun toggleFavorite(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(id, isFavorite)
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
} 