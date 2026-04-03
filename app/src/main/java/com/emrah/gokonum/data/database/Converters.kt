package com.emrah.gokonum.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun fromLocationList(locations: List<SavedLocation>?): String? {
        return gson.toJson(locations)
    }

    @TypeConverter
    fun toLocationList(data: String?): List<SavedLocation>? {
        if (data.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<SavedLocation>>() {}.type
        return gson.fromJson(data, type)
    }
}
