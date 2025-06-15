package com.np.webapiapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.np.webapiapp.data.model.Dataset
import java.util.Date

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

class ListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromResourceList(value: List<Dataset.Resource>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toResourceList(value: String): List<Dataset.Resource> {
        val listType = object : TypeToken<List<Dataset.Resource>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromTagList(value: List<Dataset.Tag>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTagList(value: String): List<Dataset.Tag> {
        val listType = object : TypeToken<List<Dataset.Tag>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromOrganization(value: Dataset.Organization): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toOrganization(value: String): Dataset.Organization {
        return gson.fromJson(value, Dataset.Organization::class.java)
    }
} 