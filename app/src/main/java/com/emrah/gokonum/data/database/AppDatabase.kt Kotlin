package com.emrah.gokonum.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emrah.gokonum.data.model.SavedLocation
import com.emrah.gokonum.data.model.SavedRoute

@Database(
    entities = [SavedLocation::class, SavedRoute::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao
    abstract fun routeDao(): RouteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gokonum_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
