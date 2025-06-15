package com.np.webapiapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DatasetDao {
    @Query("SELECT * FROM datasets")
    fun getAllDatasets(): Flow<List<DatasetEntity>>

    @Query("SELECT * FROM datasets WHERE isFavorite = 1")
    fun getFavoriteDatasets(): Flow<List<DatasetEntity>>

    @Query("SELECT * FROM datasets WHERE id = :datasetId")
    suspend fun getDatasetById(datasetId: String): DatasetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataset(dataset: DatasetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDatasets(datasets: List<DatasetEntity>)

    @Update
    suspend fun updateDataset(dataset: DatasetEntity)

    @Query("UPDATE datasets SET isFavorite = :isFavorite WHERE id = :datasetId")
    suspend fun updateFavoriteStatus(datasetId: String, isFavorite: Boolean)

    @Query("DELETE FROM datasets WHERE id = :datasetId")
    suspend fun deleteDataset(datasetId: String)

    @Query("DELETE FROM datasets")
    suspend fun deleteAllDatasets()
} 