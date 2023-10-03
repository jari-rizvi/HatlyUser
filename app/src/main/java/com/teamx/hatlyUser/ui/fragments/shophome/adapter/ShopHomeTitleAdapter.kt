package com.teamx.hatlyUser.ui.fragments.shophome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemShopHomeTitleBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface

class ShopHomeTitleAdapter(private val addressArrayList: ArrayList<com.teamx.hatlyUser.ui.fragments.shophome.model.Doc>, val hatlyShopInterface: HatlyShopInterface) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemShopHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

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

class HatlyPopularViewHolder(private var binding: ItemShopHomeTitleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}