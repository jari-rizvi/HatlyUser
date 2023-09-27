package com.teamx.hatlyUser.ui.fragments.profile.orderdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemOrderDetailBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Product

class OrderDetailAdapter(
    private val addressArrayList: ArrayList<Product>
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.txtCat.text = try {
            arrayData.productName
        }catch (e : Exception){
            ""
        }

        holder.bind.txtCat11.text = try {
            "${arrayData.prize} Aed"
        }catch (e : Exception){
            ""
        }


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemOrderDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}