package com.emrah.gokonum.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "saved_location")
data class SavedLocation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val note: String = "",
    val date: Date = Date()
)
