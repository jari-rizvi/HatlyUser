package com.teamx.hatlyUser.ui.fragments.homeSearch.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemShopHomeRecentBinding
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Product
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.home.model.FcmModel

class HomeRecentSearchAdapter(
    private val addressArrayList: ArrayList<String>,
    private val recentHomeSearchInterface: RecentHomeSearchInterface
) : RecyclerView.Adapter<HomeRecentSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecentSearchViewHolder {
        return HomeRecentSearchViewHolder(
            ItemShopHomeRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HomeRecentSearchViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.txtTitle.text = arrayData


        holder.itemView.setOnClickListener {
            recentHomeSearchInterface.clickRecent(position)
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HomeRecentSearchViewHolder(private var binding: ItemShopHomeRecentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}

interface RecentHomeSearchInterface {
    fun clickRecent(position : Int)
}