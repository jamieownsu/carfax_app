package com.chalupin.carfax_mvvm.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chalupin.carfax_mvvm.data.Listing
import com.chalupin.carfax_mvvm.databinding.ListingItemBinding

class RecyclerListAdapter : RecyclerView.Adapter<ListingViewHolder>() {
    private var listings = mutableListOf<Listing>()

    fun setListingsList(movies: List<Listing>) {
        this.listings = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListingItemBinding.inflate(inflater, parent, false)
        return ListingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        try {
            val listing = listings[position]
            holder.binding.listing = listing
        } catch (e: Exception) {
            Log.e("RecyclerListAdapter", "onBindViewHolder", e)
        }
    }

    override fun getItemCount(): Int {
        return listings.size
    }
}

class ListingViewHolder(val binding: ListingItemBinding) : RecyclerView.ViewHolder(binding.root)