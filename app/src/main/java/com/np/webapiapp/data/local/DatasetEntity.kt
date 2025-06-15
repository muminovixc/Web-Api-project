package com.np.webapiapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.np.webapiapp.data.model.Dataset

@Entity(tableName = "datasets")
data class DatasetEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val title: String,
    val description: String?,
    val organization: String?,
    val tags: List<String>,
    val resources: List<ResourceEntity>,
    val metadataCreated: String?,
    val metadataModified: String?,
    val isFavorite: Boolean = false
) {
    fun toDataset() = Dataset(
        id = id,
        name = name,
        title = title,
        description = description,
        organization = organization,
        tags = tags,
        resources = resources.map { it.toResource() },
        metadataCreated = metadataCreated,
        metadataModified = metadataModified,
        isFavorite = isFavorite
    )

    companion object {
        fun fromDataset(dataset: Dataset) = DatasetEntity(
            id = dataset.id,
            name = dataset.name,
            title = dataset.title,
            description = dataset.description,
            organization = dataset.organization,
            tags = dataset.tags,
            resources = dataset.resources.map { ResourceEntity.fromResource(it) },
            metadataCreated = dataset.metadataCreated,
            metadataModified = dataset.metadataModified,
            isFavorite = dataset.isFavorite
        )
    }
}

@Entity(tableName = "resources")
data class ResourceEntity(
    val id: String,
    val name: String,
    val format: String,
    val url: String,
    val size: Long?,
    val created: String?,
    val lastModified: String?
) {
    fun toResource() = com.np.webapiapp.data.model.Resource(
        id = id,
        name = name,
        format = format,
        url = url,
        size = size,
        created = created,
        lastModified = lastModified
    )

    companion object {
        fun fromResource(resource: com.np.webapiapp.data.model.Resource) = ResourceEntity(
            id = resource.id,
            name = resource.name,
            format = resource.format,
            url = resource.url,
            size = resource.size,
            created = resource.created,
            lastModified = resource.lastModified
        )
    }
} 