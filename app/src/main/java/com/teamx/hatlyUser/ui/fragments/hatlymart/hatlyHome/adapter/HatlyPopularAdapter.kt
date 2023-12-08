package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemPopularBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.popularproductmodel.Doc

class HatlyPopularAdapter(
    private val addressArrayList: ArrayList<Doc>,
    val hatlyShopInterface: HatlyShopInterface,
    private val addToCartInterface: AddToCartInterface
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.txtTitle.text = try {
            arrayData.name
        } catch (e: Exception) {
            ""
        }

        var salesPrice = if (arrayData.salePrice == 0.0) "" else "${arrayData.salePrice} ${holder.itemView.context.getString(R.string.aed)}"
        val price = "${arrayData.price} ${holder.itemView.context.getString(R.string.aed)}"


        if (arrayData.salePrice != 0.0){
            holder.bind.textView27.paintFlags = holder.bind.textView27.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.bind.textView27.visibility = View.VISIBLE
        }else{
            holder.bind.textView27.visibility = View.GONE
            salesPrice = price
        }

        holder.bind.txtPrize.text = try {
            salesPrice
        } catch (e: Exception) {
            ""
        }

        holder.bind.textView27.text = try {
            price
        } catch (e: Exception) {
            ""
        }

        holder.bind.txtQty.text = try {
            "${arrayData.cartQuantity}"
        } catch (e: Exception) {
            ""
        }


        Picasso.get().load(arrayData.images!![0]).resize(500,500).into(holder.bind.imgShop)


        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }

        if (arrayData.cartExistence){
            holder.bind.imgAdd.visibility = View.GONE
            holder.bind.layoutQty.visibility = View.VISIBLE
        }else{
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

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(binding: ItemPopularBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}

interface AddToCartInterface{
    fun addProduct(position: Int)
    fun updateQuantity(position: Int, quantity: Int)
}