package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemShopHomeTitleBinding
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Product
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface

class FoodHomeTitleAdapter(private val addressArrayList: ArrayList<Product>, val hatlyShopInterface: HatlyShopInterface) : RecyclerView.Adapter<FoodHomeTitleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHomeTitleViewHolder {
        return FoodHomeTitleViewHolder(
            ItemShopHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: FoodHomeTitleViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        val resources: Resources = MainApplication.context.resources

        holder.bind.txtTitle.background = ResourcesCompat.getDrawable(resources, R.drawable.txt_food_category,null)

        holder.bind.txtTitle.isChecked = arrayData.isSelected

        holder.bind.txtTitle.text = try {
            arrayData._id
        }catch (e : Exception){
            "null"
        }

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickCategoryItem(position)
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