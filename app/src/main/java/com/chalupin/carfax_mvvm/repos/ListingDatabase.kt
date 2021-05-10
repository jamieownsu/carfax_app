package com.chalupin.carfax_mvvm.repos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chalupin.carfax_mvvm.data.Listing
import com.chalupin.carfax_mvvm.data.ListingDao

@Database(entities = [Listing::class], version = 1, exportSchema = false)
abstract class ListingDatabase : RoomDatabase() {
    abstract fun listingDao(): ListingDao

    companion object {
        @Volatile
        private var INSTANCE: ListingDatabase? = null

        fun getDatabase(context: Context): ListingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ListingDatabase::class.java,
                    "listing_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}