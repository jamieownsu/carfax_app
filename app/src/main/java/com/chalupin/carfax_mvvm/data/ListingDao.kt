package com.chalupin.carfax_mvvm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ListingDao {
    @Query("SELECT * FROM listing_table")
    fun getAll(): List<Listing>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listing: List<Listing>)
}
