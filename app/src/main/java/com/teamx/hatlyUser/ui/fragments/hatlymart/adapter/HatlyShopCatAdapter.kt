package com.teamx.hatlyUser.ui.fragments.hatlymart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.MainApplication.Companion.context
import com.teamx.hatlyUser.databinding.ItemShopCatBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.interfaces.HatlyShopInterface

class HatlyShopCatAdapter(
    private val addressArrayList: ArrayList<String>,
    val hatlyShopInterface: HatlyShopInterface
) :
    RecyclerView.Adapter<AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemShopCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        if (arrayData.isNotEmpty()) {
            holder.bind.imgMore.visibility = View.VISIBLE
            holder.bind.imgShop.visibility = View.GONE
            holder.bind.txtTitle.text = arrayData
        } else {
            holder.bind.imgMore.visibility = View.GONE
            holder.bind.imgShop.visibility = View.VISIBLE
        }

        holder.bind.imgShop.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }

        holder.bind.imgMore.setOnClickListener {
            hatlyShopInterface.clickMoreItem(position)
        }


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class AddressViewHolder(private var binding: ItemShopCatBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}