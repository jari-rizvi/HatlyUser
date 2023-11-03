package com.teamx.hatlyUser.ui.fragments.homeSearch.adapter

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
import com.teamx.hatlyUser.ui.fragments.home.model.FcmModel

class HomeSearchTitleAdapter(
    private val addressArrayList: ArrayList<FcmModel>,
    val categoryHomeSearchInterface: CategoryHomeSearchInterface
) : RecyclerView.Adapter<FoodHomeTitleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHomeTitleViewHolder {
        return FoodHomeTitleViewHolder(
            ItemShopHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: FoodHomeTitleViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.txtTitle.text = arrayData.message

        holder.bind.txtTitle.isChecked = arrayData.success

        holder.itemView.setOnClickListener {
            categoryHomeSearchInterface.clickcategory(position)
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

interface CategoryHomeSearchInterface {
    fun clickcategory(position : Int)
}