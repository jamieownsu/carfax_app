package com.chalupin.carfax_mvvm.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chalupin.carfax_mvvm.data.CarFax
import com.chalupin.carfax_mvvm.data.Listing
import com.chalupin.carfax_mvvm.repos.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val listingList = MutableLiveData<List<Listing>>()
    val errorMessage = MutableLiveData<String>()

    fun getListingsOffline(context: Context, mSwipeRefreshLayout: SwipeRefreshLayout?) {
        CoroutineScope(Dispatchers.IO).launch {
            val listings = repository.getData(context)
            listingList.postValue(listings)
        }
        mSwipeRefreshLayout!!.isRefreshing = false
    }

    fun getListings(context: Context, mSwipeRefreshLayout: SwipeRefreshLayout?) {
        val response = repository.getListings()
        response.enqueue(object : Callback<CarFax> {
            override fun onResponse(call: Call<CarFax>, response: Response<CarFax>) {
                listingList.postValue(response.body()?.listings)
                response.body()?.listings?.let { repository.insertData(context, it) }
                mSwipeRefreshLayout!!.isRefreshing = false
            }

            override fun onFailure(call: Call<CarFax>, t: Throwable) {
                errorMessage.postValue(t.message)
                mSwipeRefreshLayout!!.isRefreshing = false
            }
        })
    }
}