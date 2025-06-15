package com.np.webapiapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "datasets")
data class Dataset(
    @PrimaryKey
    val id: String,
    val name: String,
    val title: String,
    val description: String?,
    val organization: String?,
    val tags: List<String>,
    val resources: List<Resource>,
    val metadataCreated: String?,
    val metadataModified: String?,
    val isFavorite: Boolean = false
)

data class Resource(
    val id: String,
    val name: String,
    val format: String,
    val url: String,
    val size: Long?,
    val created: String?,
    val lastModified: String?
)

data class DatasetResponse(
    @SerializedName("result")
    val result: List<Dataset>,
    @SerializedName("count")
    val count: Int
) 