package com.teamx.hatlyUser.ui.fragments.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemProductVariationRadioBinding

class ProductVariationRequiredAdapter(private val addressArrayList: ArrayList<String>) : RecyclerView.Adapter<RequiredViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequiredViewHolder {
        return RequiredViewHolder(
            ItemProductVariationRadioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: RequiredViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class RequiredViewHolder(private var binding: ItemProductVariationRadioBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}