
package com.emrah.gokonum.data.model

import java.util.Date

data class SavedRoute(
    val id: Long = 0,
    val name: String,
    val totalDistance: Double,     // km
    val duration: String,          // "45 dk" gibi
    val date: Date = Date(),
    val locations: List<SavedLocation> = emptyList()
)
