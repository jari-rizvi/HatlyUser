package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemStoresBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.Doc
import com.teamx.hatlyUser.utils.TimeFormatter

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
            arrayData.setting.location.formattedAddress
//            arrayData.address.googleMapAddress
        }
        catch (e : java.lang.Exception){
            ""
        }

        holder.bind.txtReview.text = try {
            "${TimeFormatter.convertNumtoArabic(arrayData.ratting.toLong())} (${TimeFormatter.convertNumtoArabic(arrayData.totalReviews.toLong())} ${holder.itemView.context.getString(R.string.reviews_)})"

        }
        catch (e : java.lang.Exception){
            ""
        }

        holder.bind.txtDuration.text = try {
            "${holder.itemView.context.getString(R.string.delivery)} ${TimeFormatter.convertNumtoArabic(arrayData.delivery.value.toLong())} ${arrayData.delivery.unit}"

        }
        catch (e : java.lang.Exception){
            ""
        }

        Picasso.get().load(arrayData.image).placeholder(R.drawable.hatly_splash_logo_space).error(R.drawable.hatly_splash_logo_space).resize(500,500).into(holder.bind.imgShop)

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