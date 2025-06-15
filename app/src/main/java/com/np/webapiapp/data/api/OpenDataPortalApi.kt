package com.np.webapiapp.data.api

import com.np.webapiapp.data.model.Dataset
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface OpenDataPortalApi {
    @GET("api/3/action/package_list")
    suspend fun getDatasetList(
        @Header("Authorization") apiKey: String
    ): List<String>

    @GET("api/3/action/package_show")
    suspend fun getDatasetDetails(
        @Header("Authorization") apiKey: String,
        @Query("id") datasetId: String
    ): Dataset

    @GET("api/3/action/package_search")
    suspend fun searchDatasets(
        @Header("Authorization") apiKey: String,
        @Query("q") query: String,
        @Query("rows") limit: Int = 20,
        @Query("start") offset: Int = 0
    ): List<Dataset>
} 