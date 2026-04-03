package com.emrah.gokonum.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emrah.gokonum.data.model.SavedRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {

    @Query("SELECT * FROM saved_route ORDER BY date DESC")
    fun getAllRoutes(): Flow<List<SavedRoute>>

    @Insert
    suspend fun insertRoute(route: SavedRoute): Long
}
