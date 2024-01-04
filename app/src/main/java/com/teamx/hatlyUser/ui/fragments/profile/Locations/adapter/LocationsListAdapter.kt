package com.teamx.hatlyUser.ui.fragments.profile.Locations.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.MainApplication
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

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        var labelStr= ""

        when (arrayData.label) {
            "Home" -> {
                holder.bind.img.setImageDrawable(context.getDrawable(R.drawable.home_label))
//                Picasso.get().load(R.drawable.home_label).into(holder.bind.img)
                labelStr = context.getString(R.string.home)
            }

            "Work" -> {
                holder.bind.img.setImageDrawable(context.getDrawable(R.drawable.work_label))
//                Picasso.get().load(R.drawable.work_label).into(holder.bind.img)
                labelStr = context.getString(R.string.work)
            }

            else -> {
                holder.bind.img.setImageDrawable(context.getDrawable(R.drawable.pin_location))
//                Picasso.get().load(R.drawable.pin_location).into(holder.bind.img)
                labelStr = arrayData.label
            }
        }

//        if (arrayData.isDefault){
//            holder.bind.textView16.setTextColor(R.color.colorGray)
//            holder.bind.textView18.setTextColor(R.color.colorLightGray)
//        }

        holder.bind.textView16.text = try {
            labelStr
        } catch (e: Exception) {
            "null"
        }

        if (arrayData.isFromSender) {
            holder.bind.img1.visibility = View.GONE
        } else {
            holder.bind.img1.visibility = View.VISIBLE
        }


        holder.bind.mainItem.isActivated = arrayData.isDefault

        if (arrayData.isDefault) {
            holder.bind.img2.visibility = View.GONE
        } else {
            holder.bind.img2.visibility = View.VISIBLE
        }

        if (arrayData.isDefault) {
            holder.bind.txtTitle.visibility = View.VISIBLE
        } else {
            holder.bind.txtTitle.visibility = View.GONE
        }




        holder.bind.textView18.text = try {
            arrayData.address
        } catch (e: Exception) {
            "null"
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