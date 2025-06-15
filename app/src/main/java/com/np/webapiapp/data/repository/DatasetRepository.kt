package com.np.webapiapp.data.repository

import com.np.webapiapp.data.api.OpenDataPortalApi
import com.np.webapiapp.data.local.DatasetDao
import com.np.webapiapp.data.local.DatasetEntity
import com.np.webapiapp.data.model.Dataset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatasetRepository @Inject constructor(
    private val api: OpenDataPortalApi,
    private val datasetDao: DatasetDao
) {
    fun getAllDatasets(): Flow<List<Dataset>> {
        return datasetDao.getAllDatasets().map { entities ->
            entities.map { it.toDataset() }
        }
    }

    fun getFavoriteDatasets(): Flow<List<Dataset>> {
        return datasetDao.getFavoriteDatasets().map { entities ->
            entities.map { it.toDataset() }
        }
    }

    suspend fun refreshDatasets(query: String? = null) {
        try {
            val response = api.getDatasets(query = query)
            val datasets = response.result
            datasetDao.insertDatasets(datasets.map { DatasetEntity.fromDataset(it) })
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun getDatasetDetails(id: String): Dataset? {
        return try {
            val response = api.getDatasetDetails(id)
            response.result.firstOrNull()?.let { dataset ->
                datasetDao.insertDataset(DatasetEntity.fromDataset(dataset))
                dataset
            }
        } catch (e: Exception) {
            datasetDao.getDatasetById(id)?.toDataset()
        }
    }

    suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        datasetDao.updateFavoriteStatus(id, isFavorite)
    }
} 