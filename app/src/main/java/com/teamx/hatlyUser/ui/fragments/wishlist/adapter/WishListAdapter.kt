package com.teamx.hatlyUser.ui.fragments.wishlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemWishlistBinding
import com.teamx.hatlyUser.ui.fragments.wishlist.modelWishList.Doc

class WishListAdapter(
    private val addressArrayList: ArrayList<Doc>
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
            ""
        }

        holder.bind.shopRate.rating = arrayData.averageRating.toFloat()

        holder.bind.textView2122.text = try {
            arrayData.averageRating.toString()
        }catch (e : Exception){
            ""
        }

        Picasso.get().load(arrayData.shop.image).resize(500,500).into(holder.bind.imgShop)

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemWishlistBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}