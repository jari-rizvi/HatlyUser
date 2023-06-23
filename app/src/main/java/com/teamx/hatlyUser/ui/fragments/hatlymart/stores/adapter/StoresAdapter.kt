package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemStoresBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStores
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStoresItem

class StoresAdapter(private val addressArrayList: ArrayList<ModelAllStoresItem>, val hatlyShopInterface: HatlyShopInterface) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemStoresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.txtTitle.text = try {
            arrayData.name
        }catch (e : java.lang.Exception){
            ""
        }


        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemStoresBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}