package com.teamx.hatlyUser.ui.fragments.profile.Locations.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.MainApplication.Companion.context
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemLocationBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.Location
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface

class LocationsListAdapter(
    private val addressArrayList: ArrayList<Location>,
    private val hatlyShopInterface: HatlyShopInterface
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        when (arrayData.label) {
            "Home" -> {
                holder.bind.img.setImageDrawable(context.getDrawable(R.drawable.home_label))
//                Picasso.get().load(R.drawable.home_label).into(holder.bind.img)
            }
            "Work" -> {
                holder.bind.img.setImageDrawable(context.getDrawable(R.drawable.work_label))
//                Picasso.get().load(R.drawable.work_label).into(holder.bind.img)
            }
            else -> {
                holder.bind.img.setImageDrawable(context.getDrawable(R.drawable.pin_location))
//                Picasso.get().load(R.drawable.pin_location).into(holder.bind.img)
            }
        }


        holder.bind.mainItem.isActivated = arrayData.isDefault

        if (arrayData.isDefault){
            holder.bind.txtTitle.visibility = View.VISIBLE
        }else{
            holder.bind.txtTitle.visibility = View.GONE
        }


        holder.bind.textView16.text = try {
            arrayData.label
        } catch (e: Exception) {
            ""
        }

        holder.bind.textView18.text = try {
            arrayData.address
        } catch (e: Exception) {
            ""
        }

        holder.bind.img1.setOnClickListener {
            hatlyShopInterface.clickCategoryItem(position)
        }

        holder.bind.img2.setOnClickListener {
            hatlyShopInterface.clickMoreItem(position)
        }

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemLocationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}