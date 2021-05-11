package com.chalupin.carfax_mvvm.data

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chalupin.carfax_mvvm.activities.VehicleDetailsActivity
import com.chalupin.carfax_mvvm.constants.Constants
import java.io.Serializable

@Entity(tableName = "listing_table")
data class Listing(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @Embedded(prefix = "dlr")
    val dealer: Dealer,
    val id: String,
    val vin: String,
    val year: Int,
    val make: String,
    val model: String,
    val mileage: Int,
    val currentPrice: Float,
    val imageCount: Int,
    @Embedded(prefix = "imgs")
    val images: Images,
    val exteriorColor: String,
    val interiorColor: String,
    val engine: String,
    val drivetype: String,
    val transmission: String,
    val fuel: String,
    val bodytype: String,
) : Serializable {
    fun callDealer(view: View) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:${this.dealer.phone}")
        if (ActivityCompat.checkSelfPermission(
                view.context,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                view.context as Activity,
                arrayOf(Manifest.permission.CALL_PHONE),
                1
            )
            val appPref = view.context.getSharedPreferences(
                Constants.HOLD_DEALER_NUMBER,
                Context.MODE_PRIVATE
            )
            val editPref = appPref.edit()
            editPref.putString(Constants.DEALER_NUMBER, this.dealer.phone).apply()
        } else
            view.context.startActivity(callIntent)
    }

    fun openDetails(view: View) {
        val intent = Intent(view.context, VehicleDetailsActivity::class.java)
        intent.putExtra(Constants.BUNDLE, this)
        view.context.startActivity(intent)
    }
}

