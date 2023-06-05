package com.teamx.hatlyUser.ui.fragments.hatlymart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemPopularBinding

class HatlyPopularAdapter(private val addressArrayList: ArrayList<String>) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemPopularBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}