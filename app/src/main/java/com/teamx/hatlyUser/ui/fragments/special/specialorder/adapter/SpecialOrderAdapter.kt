package com.teamx.hatlyUser.ui.fragments.special.specialorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemSpecialOrderBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.special.specialorder.model.ActiveParcel
import com.teamx.hatlyUser.ui.fragments.special.specialorder.model.DeliveredParcel

class SpecialOrderAdapter(
    private val addressArrayList: ArrayList<DeliveredParcel>,
    private val hatlyShopInterface: HatlyShopInterface
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemSpecialOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.textView222.text = try {
            "Tracking ID: ${arrayData.trackingNumber}"
        } catch (e: Exception) {
            ""
        }

        holder.bind.textView22725.text = try {
            arrayData.pickup.address
        } catch (e: Exception) {
            ""
        }

        holder.bind.textView227925.text = try {
            arrayData.dropOff.address
        } catch (e: Exception) {
            ""
        }

        holder.bind.textView22795544525.text = try {
            "${arrayData.fare} Aed"
        }catch (e : Exception){
            ""
        }

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemSpecialOrderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}