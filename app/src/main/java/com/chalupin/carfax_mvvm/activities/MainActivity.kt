package com.chalupin.carfax_mvvm.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chalupin.carfax_mvvm.adapters.RecyclerListAdapter
import com.chalupin.carfax_mvvm.constants.Constants
import com.chalupin.carfax_mvvm.databinding.ActivityMainBinding
import com.chalupin.carfax_mvvm.models.MainViewModel
import com.chalupin.carfax_mvvm.models.MainViewModelFactory
import com.chalupin.carfax_mvvm.repos.MainRepository
import com.chalupin.carfax_mvvm.repos.Webservice

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private val webservice = Webservice.getInstance()
    private val adapter = RecyclerListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mSwipeRefreshLayout = binding.swipeView
        mSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getListings(mSwipeRefreshLayout)
        }
        binding.listings.adapter = adapter
        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(MainRepository(webservice))
            ).get(
                MainViewModel::class.java
            )
        viewModel.listingList.observe(this, {
            adapter.setListingsList(it.listings)
        })
        viewModel.errorMessage.observe(this, {

        })
        viewModel.getListings(mSwipeRefreshLayout)
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