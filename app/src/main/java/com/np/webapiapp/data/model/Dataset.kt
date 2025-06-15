package com.np.webapiapp.data.model

data class Dataset(
    val id: String,
    val name: String,
    val title: String,
    val description: String,
    val resources: List<Resource>,
    val organization: Organization,
    val tags: List<Tag>,
    val metadata_created: String,
    val metadata_modified: String
)

data class Resource(
    val id: String,
    val name: String,
    val format: String,
    val url: String,
    val description: String,
    val created: String,
    val last_modified: String
)

data class Organization(
    val id: String,
    val name: String,
    val title: String,
    val description: String
)

data class Tag(
    val id: String,
    val name: String,
    val display_name: String
) 