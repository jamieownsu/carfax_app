package com.chalupin.carfax_mvvm.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chalupin.carfax_mvvm.R
import com.chalupin.carfax_mvvm.constants.Constants
import com.chalupin.carfax_mvvm.data.Listing
import com.chalupin.carfax_mvvm.databinding.ActivityVehicleDetailsBinding

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