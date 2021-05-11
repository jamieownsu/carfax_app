package com.chalupin.carfax_mvvm.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chalupin.carfax_mvvm.R
import com.chalupin.carfax_mvvm.constants.Constants
import com.chalupin.carfax_mvvm.data.Listing
import com.chalupin.carfax_mvvm.databinding.ActivityVehicleDetailsBinding
import com.chalupin.carfax_mvvm.utilities.MainUtilities

class VehicleDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val binding: ActivityVehicleDetailsBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_vehicle_details
        )
        val listing = intent.extras!!.getSerializable(Constants.BUNDLE) as Listing
        binding.listing = listing
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        MainUtilities.permissionHandler(this, requestCode, grantResults)
    }
}