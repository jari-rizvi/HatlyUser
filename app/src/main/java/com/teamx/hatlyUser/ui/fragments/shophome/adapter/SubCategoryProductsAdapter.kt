package com.teamx.hatlyUser.ui.fragments.shophome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemPopularBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.AddToCartInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.shophome.model.Document

class SubCategoryProductsAdapter(
    private val addressArrayList: ArrayList<Document>,
    val hatlyShopInterface: HatlyShopInterface,
    private val addToCartInterface: AddToCartInterface
) : RecyclerView.Adapter<SubCategoryProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryProductsViewHolder {
        return SubCategoryProductsViewHolder(
            ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: SubCategoryProductsViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.txtTitle.text = try {
            arrayData.name
        } catch (e: Exception) {
            ""
        }

        holder.bind.txtPrize.text = try {
            "${arrayData.prize}"
        } catch (e: Exception) {
            ""
        }

        Picasso.get().load(arrayData.images[0]).resize(500,500).into(holder.bind.imgShop)

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

        holder.bind.imgAdd.setOnClickListener {
            addToCartInterface.addProduct(position)
        }

        holder.bind.imgIncreament.setOnClickListener {
            addToCartInterface.updateQuantity(position, arrayData.cartQuantity + 1)
        }

        holder.bind.imgDeccreament.setOnClickListener {
            addToCartInterface.updateQuantity(position, arrayData.cartQuantity - 1)
        }


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class SubCategoryProductsViewHolder(private var binding: ItemPopularBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}