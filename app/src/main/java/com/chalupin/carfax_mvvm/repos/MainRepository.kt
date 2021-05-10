package com.chalupin.carfax_mvvm.repos

class MainRepository(private val webservice: Webservice) {
    fun getListings() = webservice.getListings()
}