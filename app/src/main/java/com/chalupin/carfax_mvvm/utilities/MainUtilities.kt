package com.chalupin.carfax_mvvm.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.chalupin.carfax_mvvm.constants.Constants

class MainUtilities {
    companion object {
        fun isNetworkAvailable(activity: Activity): Boolean {
            val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetwork: NetworkInfo? = null
            activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

        fun permissionHandler(
            activity: Activity,
            requestCode: Int,
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
                    val appPref = activity.getSharedPreferences(
                        Constants.HOLD_DEALER_NUMBER,
                        AppCompatActivity.MODE_PRIVATE
                    )
                    val number = appPref.getString(Constants.DEALER_NUMBER, "")
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:$number")
                    activity.startActivity(callIntent)
                }
            }
        }
    }
}