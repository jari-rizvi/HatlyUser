package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemStoresBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.Doc

class StoresAdapter(private val addressArrayList: ArrayList<Doc>, val hatlyShopInterface: HatlyShopInterface) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

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

        holder.bind.txtAddress.text = try {
            arrayData.address.googleMapAddress
        }
        catch (e : java.lang.Exception){
            ""
        }

        holder.bind.txtReview.text = try {
            "${arrayData.ratting} (${arrayData.totalReviews} reviews)"

        }
        catch (e : java.lang.Exception){
            ""
        }

        holder.bind.txtDuration.text = try {
            "Delivery ${arrayData.delivery?.value} ${arrayData.delivery?.unit}"

        }
        catch (e : java.lang.Exception){
            ""
        }

        Picasso.get().load(arrayData.image).into(holder.bind.imgShop)

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