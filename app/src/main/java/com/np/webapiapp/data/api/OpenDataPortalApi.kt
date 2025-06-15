package com.np.webapiapp.data.api

import com.np.webapiapp.data.model.DatasetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenDataPortalApi {
    @GET("api/3/action/package_list")
    suspend fun getDatasets(
        @Query("q") query: String? = null,
        @Query("rows") limit: Int = 20,
        @Query("start") offset: Int = 0
    ): DatasetResponse

    @GET("api/3/action/package_show")
    suspend fun getDatasetDetails(
        @Query("id") id: String
    ): DatasetResponse
} 