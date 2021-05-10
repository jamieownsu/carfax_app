package com.chalupin.carfax_mvvm.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chalupin.carfax_mvvm.adapters.RecyclerListAdapter
import com.chalupin.carfax_mvvm.constants.Constants
import com.chalupin.carfax_mvvm.databinding.ActivityMainBinding
import com.chalupin.carfax_mvvm.models.MainViewModel
import com.chalupin.carfax_mvvm.models.MainViewModelFactory
import com.chalupin.carfax_mvvm.repos.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            viewModel.insertData(this, it)
            binding.loading = false
        })
        viewModel.errorMessage.observe(this, {
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        })
        getListingData()
    }

    private fun getListingData() {
        if (isNetworkAvailable())
            viewModel.getListings(this, mSwipeRefreshLayout)
        else {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getListingsOffline(applicationContext, mSwipeRefreshLayout)
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetwork: NetworkInfo? = null
        activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            var permissionGranted = false
            for (i in grantResults) {
                if (i == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true
                    break
                }
            }
            if (permissionGranted) {
                val appPref = getSharedPreferences(Constants.HOLD_DEALER_NUMBER, MODE_PRIVATE)
                val number = appPref.getString(Constants.DEALER_NUMBER, "")
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$number")
                startActivity(callIntent)
            }
        }
    }
}