package com.teamx.hatlyUser.ui.fragments.wishlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemWishlistBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.wishlist.modelWishList.Doc

class WishListAdapter(
    private val addressArrayList: ArrayList<Doc>,
    private val hatlyShopInterface: HatlyShopInterface
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.textView21.text = try {
            arrayData.shop.name
        }catch (e : Exception){
            ""
        }

        holder.bind.txtDelivery.text = try {
           "Delivery ${arrayData.shop.delivery.value} ${arrayData.shop.delivery.unit}"
        }catch (e : Exception){
            "0.0"
        }

        holder.bind.imgFavourite.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickCategoryItem(position)
        }

        holder.bind.shopRate.rating = arrayData.averageRating.toFloat()

//        val rating = String.format("%.1f", arrayData.averageRating).toFloat()

        holder.bind.textView2122.text = try {
            "${arrayData.averageRating.toFloat()}".substring(0,3)
        }catch (e : Exception){
            ""
        }

        Picasso.get().load(arrayData.shop.image).placeholder(R.drawable.hatly_splash_logo_space).error(
            R.drawable.hatly_splash_logo_space).resize(500,500).into(holder.bind.imgShop)

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemWishlistBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}