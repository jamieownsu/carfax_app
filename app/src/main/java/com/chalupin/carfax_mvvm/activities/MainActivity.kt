package com.chalupin.carfax_mvvm.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chalupin.carfax_mvvm.adapters.RecyclerListAdapter
import com.chalupin.carfax_mvvm.databinding.ActivityMainBinding
import com.chalupin.carfax_mvvm.models.MainViewModel
import com.chalupin.carfax_mvvm.models.MainViewModelFactory
import com.chalupin.carfax_mvvm.repos.MainRepository
import com.chalupin.carfax_mvvm.utilities.MainUtilities

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private val adapter = RecyclerListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mSwipeRefreshLayout = binding.swipeView
        mSwipeRefreshLayout.setOnRefreshListener {
            getListingData()
        }
        binding.listings.adapter = adapter
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository())
        ).get(MainViewModel::class.java)
        viewModel.listingList.observe(this, {
            adapter.setListingsList(it)
            binding.loading = false
        })
        viewModel.errorMessage.observe(this, {
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        })
        getListingData()
    }

    private fun getListingData() {
        if (MainUtilities.isNetworkAvailable(this))
            viewModel.getListings(this, mSwipeRefreshLayout)
        else {
            viewModel.getListingsOffline(this, mSwipeRefreshLayout)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        MainUtilities.permissionHandler(this, requestCode, grantResults)
    }
}