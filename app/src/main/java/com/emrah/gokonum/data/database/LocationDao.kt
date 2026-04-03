package com.emrah.gokonum.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emrah.gokonum.data.model.SavedLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM saved_location ORDER BY date DESC")
    fun getAllLocations(): Flow<List<SavedLocation>>

    @Insert
    suspend fun insertLocation(location: SavedLocation): Long

    @Query("DELETE FROM saved_location WHERE id = :id")
    suspend fun deleteLocation(id: Long)
}
