package com.chalupin.carfax_mvvm.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chalupin.carfax_mvvm.R
import com.chalupin.carfax_mvvm.data.Listing
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String) {
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(view)
        }

        @JvmStatic
        @BindingAdapter("setPriceMiles")
        fun setPriceMiles(view: TextView, listing: Listing) {
            var numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
            numberFormat.maximumFractionDigits = 0
            val price = numberFormat.format(listing.currentPrice)
            numberFormat = NumberFormat.getInstance()
            numberFormat.maximumFractionDigits = 1
            val mileage = numberFormat.format(
                listing.mileage.div(1000)
            )
            view.text =
                String.format(Locale.getDefault(), "%s | %sk mi", price, mileage)
        }
    }
}