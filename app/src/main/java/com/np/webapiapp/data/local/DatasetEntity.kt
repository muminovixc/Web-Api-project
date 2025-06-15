package com.np.webapiapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.np.webapiapp.data.model.Dataset
import com.np.webapiapp.data.local.converters.DateConverter
import com.np.webapiapp.data.local.converters.ListConverter
import java.util.Date

@Entity(tableName = "datasets")
@TypeConverters(DateConverter::class, ListConverter::class)
data class DatasetEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val title: String,
    val description: String,
    val resources: List<Dataset.Resource>,
    val organization: Dataset.Organization,
    val tags: List<Dataset.Tag>,
    val metadataCreated: Date,
    val metadataModified: Date,
    val isFavorite: Boolean = false,
    val lastUpdated: Date = Date()
) 