package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.MainApplication.Companion.context
import com.teamx.hatlyUser.R
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

        var textView26Price = ""
        var textView27Price = ""


        if (arrayData.productType == "simple") {

            textView26Price = if (arrayData.salePrice == 0.0) "" else "${arrayData.salePrice} ${context.getString(
                R.string.aed)}"

            textView27Price = "${arrayData.price} ${context.getString(
                R.string.aed)}"

            Log.d("FoodsShopProductAdapter", "onBindViewHolder: working $position")

            holder.bind.textView27.paintFlags = holder.bind.textView27.paintFlags

            if (arrayData.salePrice != 0.0) {
                holder.bind.textView27.paintFlags = holder.bind.textView27.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        } else {
            textView26Price = "${arrayData.minPrice} ${context.getString(
                R.string.aed)}"
            textView27Price = "${arrayData.maxPrice} ${context.getString(
                R.string.aed)}"
        }


        holder.bind.textView26.text = try {
            textView26Price
        } catch (e: Exception) {
            "null"
        }

        holder.bind.textView27.text = try {
            textView27Price
        } catch (e: Exception) {
            "null"
        }




        Picasso.get().load(arrayData.images[0]).placeholder(R.drawable.hatly_splash_logo_space).error(R.drawable.hatly_splash_logo_space).resize(500, 500).into(holder.bind.imgShop)

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