package com.chalupin.carfax_mvvm.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chalupin.carfax_mvvm.data.CarFax
import com.chalupin.carfax_mvvm.repos.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {
    val listingList = MutableLiveData<CarFax>()
    val errorMessage = MutableLiveData<String>()

    fun getListings(mSwipeRefreshLayout: SwipeRefreshLayout?) {
        val response = repository.getListings()
        response.enqueue(object : Callback<CarFax> {
            override fun onResponse(call: Call<CarFax>, response: Response<CarFax>) {
                listingList.postValue(response.body())
                mSwipeRefreshLayout!!.isRefreshing = false
            }

            override fun onFailure(call: Call<CarFax>, t: Throwable) {
                errorMessage.postValue(t.message)
                mSwipeRefreshLayout!!.isRefreshing = false
            }
        })
    }
}