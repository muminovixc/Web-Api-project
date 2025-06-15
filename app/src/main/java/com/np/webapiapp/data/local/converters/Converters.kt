package com.np.webapiapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.np.webapiapp.data.local.ResourceEntity

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromResourceList(value: String): List<ResourceEntity> {
        val listType = object : TypeToken<List<ResourceEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toResourceList(list: List<ResourceEntity>): String {
        return gson.toJson(list)
    }
} 