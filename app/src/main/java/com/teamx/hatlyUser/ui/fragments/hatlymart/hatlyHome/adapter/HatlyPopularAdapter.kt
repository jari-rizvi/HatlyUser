package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemPopularBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.PopularProduct

class HatlyPopularAdapter(
    private val addressArrayList: ArrayList<PopularProduct>,
    val hatlyShopInterface: HatlyShopInterface
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
        }catch (e : Exception){
            ""
        }

        holder.bind.txtPrize.text = try {
            arrayData.prize
        }catch (e : Exception){
            ""
        }

        Picasso.get().load(arrayData.images[0].secure_url).into(holder.bind.imgShop)

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }




    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemPopularBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}