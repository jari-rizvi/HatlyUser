package com.teamx.hatlyUser.ui.fragments.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemProductVariationCheckBinding

class ProductVariationOptionalAdapter(private val addressArrayList: ArrayList<String>) : RecyclerView.Adapter<OptionalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionalViewHolder {
        return OptionalViewHolder(
            ItemProductVariationCheckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: OptionalViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class OptionalViewHolder(private var binding: ItemProductVariationCheckBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}