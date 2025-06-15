package com.np.webapiapp.data.repository

import com.np.webapiapp.data.api.OpenDataPortalApi
import com.np.webapiapp.data.local.DatasetDao
import com.np.webapiapp.data.local.DatasetEntity
import com.np.webapiapp.data.model.Dataset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class DatasetRepository(
    private val api: OpenDataPortalApi,
    private val dao: DatasetDao,
    private val apiKey: String
) {
    fun getAllDatasets(): Flow<List<Dataset>> {
        return dao.getAllDatasets().map { entities ->
            entities.map { it.toDataset() }
        }
    }

    fun getFavoriteDatasets(): Flow<List<Dataset>> {
        return dao.getFavoriteDatasets().map { entities ->
            entities.map { it.toDataset() }
        }
    }

    suspend fun refreshDatasets() {
        try {
            val datasetIds = api.getDatasetList(apiKey)
            val datasets = datasetIds.map { id ->
                api.getDatasetDetails(apiKey, id)
            }
            dao.insertDatasets(datasets.map { it.toEntity() })
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun searchDatasets(query: String): List<Dataset> {
        return try {
            api.searchDatasets(apiKey, query)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun toggleFavorite(datasetId: String, isFavorite: Boolean) {
        dao.updateFavoriteStatus(datasetId, isFavorite)
    }

    private fun Dataset.toEntity(): DatasetEntity {
        return DatasetEntity(
            id = id,
            name = name,
            title = title,
            description = description,
            resources = resources,
            organization = organization,
            tags = tags,
            metadataCreated = Date(),
            metadataModified = Date()
        )
    }

    private fun DatasetEntity.toDataset(): Dataset {
        return Dataset(
            id = id,
            name = name,
            title = title,
            description = description,
            resources = resources,
            organization = organization,
            tags = tags,
            metadata_created = metadataCreated.toString(),
            metadata_modified = metadataModified.toString()
        )
    }
} 