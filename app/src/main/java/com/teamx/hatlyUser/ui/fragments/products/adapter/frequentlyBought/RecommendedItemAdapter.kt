package com.teamx.hatlyUser.ui.fragments.products.adapter.frequentlyBought

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemPopularBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.AddToCartInterface
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.model.Recommended

class RecommendedItemAdapter(
    private val addressArrayList: ArrayList<Recommended>,
    private val productPreInterface: ProductPreviewInterface,
    private val addToCartInterface: AddToCartInterface
) : RecyclerView.Adapter<FrequentlyBoughtViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrequentlyBoughtViewHolder {
        return FrequentlyBoughtViewHolder(
            ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: FrequentlyBoughtViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        holder.bind.txtTitle.text = try {
            arrayData.name
        } catch (e: Exception) {
            ""
        }


        var textView26Price = ""
        var textView27Price = ""


        if (arrayData.productType == "simple") {

            textView26Price = if (arrayData.salePrice == 0.0) "" else "${arrayData.salePrice} ${
                MainApplication.context.getString(
                R.string.aed)}"

            textView27Price = "${arrayData.price} ${
                MainApplication.context.getString(
                R.string.aed)}"

            Log.d("FoodsShopProductAdapter", "onBindViewHolder: working $position")

            holder.bind.textView27.paintFlags = holder.bind.textView27.paintFlags

            if (arrayData.salePrice != 0.0) {
                holder.bind.textView27.paintFlags =
                    holder.bind.textView27.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        } else {
            textView26Price = "${arrayData.minPrice} ${
                MainApplication.context.getString(
                R.string.aed)}"
            textView27Price = "${arrayData.maxPrice} ${
                MainApplication.context.getString(
                R.string.aed)}"
        }


        holder.bind.txtPrize.text = try {
            textView26Price
        } catch (e: Exception) {
            ""
        }

        holder.bind.textView27.text = try {
            textView27Price
        } catch (e: Exception) {
            ""
        }

        holder.bind.txtQty.text = try {
            "${arrayData.cartQuantity}"
        } catch (e: Exception) {
            ""
        }

        Picasso.get().load(arrayData.images[0]).placeholder(R.drawable.hatly_splash_logo_space).error(R.drawable.hatly_splash_logo_space).resize(500, 500).into(holder.bind.imgShop)

        if (arrayData.quantity == 0){
            holder.bind.stockLayout.visibility = View.VISIBLE
            holder.bind.txtTitle54.visibility = View.VISIBLE
        }

        if (arrayData.cartExistence) {
            holder.bind.imgAdd.visibility = View.GONE
            holder.bind.layoutQty.visibility = View.VISIBLE
        } else {
            holder.bind.imgAdd.visibility = View.VISIBLE
            holder.bind.layoutQty.visibility = View.GONE
        }

        holder.bind.imgIncreament.setOnClickListener {
            addToCartInterface.updateQuantity(position, arrayData.cartQuantity + 1)
        }

        holder.bind.imgDeccreament.setOnClickListener {
            addToCartInterface.updateQuantity(position, arrayData.cartQuantity - 1)
        }

        holder.bind.imgAdd.setOnClickListener {
            addToCartInterface.addProduct(position)
        }

        holder.itemView.setOnClickListener {
            productPreInterface.clickFreBoughtItem(position)
        }


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class FrequentlyBoughtViewHolder(private var binding: ItemPopularBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}