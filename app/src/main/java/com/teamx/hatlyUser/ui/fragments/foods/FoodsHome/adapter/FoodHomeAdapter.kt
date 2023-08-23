package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemFoodsHomeBinding
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelShops.Doc
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface

class FoodHomeAdapter(
    private val foodsAllShopsArrayList: ArrayList<Doc>,
    val hatlyShopInterface: HatlyShopInterface
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemFoodsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val modelShops = foodsAllShopsArrayList[position]

        holder.bind.shopRate.rating = modelShops.rattingCount.`$numberDecimal`.toFloat()

        holder.bind.txtTitle.text = try {
            modelShops.name
        }catch (e : Exception){
            ""
        }

        Picasso.get().load(modelShops.image.secure_url).into(holder.bind.imgShop)

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }


    }

    override fun getItemCount(): Int {
        return foodsAllShopsArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemFoodsHomeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}