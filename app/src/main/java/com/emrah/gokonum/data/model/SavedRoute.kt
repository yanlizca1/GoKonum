package com.emrah.gokonum.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "saved_route")
data class SavedRoute(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val totalDistance: Double,
    val duration: String,
    val date: Date = Date(),
    val locations: List<SavedLocation> = emptyList()
)
