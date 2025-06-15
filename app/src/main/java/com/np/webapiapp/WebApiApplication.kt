package com.np.webapiapp

import android.app.Application
import com.np.webapiapp.data.api.OpenDataPortalApi
import com.np.webapiapp.data.local.AppDatabase
import com.np.webapiapp.data.repository.DatasetRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebApiApplication : Application() {
    private val apiKey = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIyMDczIiwibmJmIjoxNzQ5OTA3NzEwLCJleHAiOjE3NDk5OTQxMTAsImlhdCI6MTc0OTkwNzcxMH0.3P57Qip_qLW-lt2RxAhpRvyMZR2nZkG_o1H2hD2uTc6DZBW1-CdvV_b6wxn_k_bNA0CMJfu023Mh5YDBsIUBAw"

    private val database by lazy {
        AppDatabase.getDatabase(this)
    }

    private val api by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://odp.iddeea.gov.ba/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenDataPortalApi::class.java)
    }

    val repository by lazy {
        DatasetRepository(api, database.datasetDao(), apiKey)
    }
} 