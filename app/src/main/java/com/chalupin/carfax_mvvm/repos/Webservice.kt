package com.chalupin.carfax_mvvm.repos

import com.chalupin.carfax_mvvm.data.CarFax
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Webservice {
    @GET("assignment.json")
    fun getListings(): Call<CarFax>

    companion object {
        var webservice: Webservice? = null
        fun getInstance(): Webservice {
            val listingsURL = "https://carfax-for-consumers.firebaseio.com/"
            if (webservice == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(listingsURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                webservice = retrofit.create(Webservice::class.java)
            }
            return webservice!!
        }
    }
}