package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemShopHomeTitleBinding
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Product
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.ProductPreviewInterface

class FoodHomeTitleAdapter(private val addressArrayList: ArrayList<Product>, val hatlyShopInterface: HatlyShopInterface) : RecyclerView.Adapter<FoodHomeTitleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHomeTitleViewHolder {
        return FoodHomeTitleViewHolder(
            ItemShopHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: FoodHomeTitleViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.txtTitle.text = try {
            arrayData._id
        }catch (e : Exception){
            "null"
        }

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class FoodHomeTitleViewHolder(private var binding: ItemShopHomeTitleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}