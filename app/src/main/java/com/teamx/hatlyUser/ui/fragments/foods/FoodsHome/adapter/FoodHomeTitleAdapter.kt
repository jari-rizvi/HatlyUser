package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemShopCatBinding

class FoodHomeTitleAdapter(private val addressArrayList: ArrayList<String>) : RecyclerView.Adapter<FoodHomeTitleAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHomeTitleAdapterViewHolder {
        return FoodHomeTitleAdapterViewHolder(
            ItemShopCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: FoodHomeTitleAdapterViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class FoodHomeTitleAdapterViewHolder(private var binding: ItemShopCatBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}