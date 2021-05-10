package com.chalupin.carfax_mvvm.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chalupin.carfax_mvvm.repos.MainRepository

class MainViewModelFactory constructor(private val repository: MainRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}