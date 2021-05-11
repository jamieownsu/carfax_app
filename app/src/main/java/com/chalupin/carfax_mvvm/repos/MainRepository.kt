package com.chalupin.carfax_mvvm.repos

import android.content.Context
import com.chalupin.carfax_mvvm.data.Listing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainRepository {
    private val webservice = Webservice.getInstance()

    companion object {
        var listingDatabase: ListingDatabase? = null

        private fun initializeDB(context: Context): ListingDatabase {
            return ListingDatabase.getDatabase(context)
        }
    }

    fun getListings() = webservice.getListings()

    fun insertData(context: Context, listings: List<Listing>) {
        listingDatabase = initializeDB(context)
        CoroutineScope(Dispatchers.IO).launch {
            listingDatabase!!.listingDao().insert(listings)
        }
    }

    fun getData(context: Context): List<Listing> {
        listingDatabase = initializeDB(context)
        return listingDatabase!!.listingDao().getAll()
    }
}