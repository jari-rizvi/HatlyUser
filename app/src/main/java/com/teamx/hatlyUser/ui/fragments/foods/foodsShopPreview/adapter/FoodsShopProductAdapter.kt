package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemFoodsShopBinding
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Document
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface

class FoodsShopProductAdapter(
    private val addressArrayList: ArrayList<Document>,
    val hatlyShopInterface: HatlyShopInterface
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemFoodsShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.textView21.text = try {
            arrayData.name
        } catch (e: Exception) {
            "null"
        }

        holder.bind.textView28.text = try {
            arrayData.description
        } catch (e: Exception) {
            "null"
        }

        holder.bind.textView26.text = try {
            arrayData.prize
        } catch (e: Exception) {
            "null"
        }

        Picasso.get().load(arrayData.images[0]).into(holder.bind.imgShop)

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemFoodsShopBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}