
package com.emrah.gokonum.data.model

import java.util.Date

data class SavedLocation(
    val id: Long = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val note: String = "",
    val date: Date = Date()
)
