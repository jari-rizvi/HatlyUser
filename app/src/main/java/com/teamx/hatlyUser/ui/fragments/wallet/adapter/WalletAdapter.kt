package com.teamx.hatlyUser.ui.fragments.wallet.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemWalletBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Doc
import com.teamx.hatlyUser.utils.TimeFormatter

class WalletAdapter(
    private val addressArrayList: ArrayList<Doc>,
    private val hatlyShopInterface: HatlyShopInterface
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        holder.bind.textView18.text = try {
            arrayData.shop.name
        }catch (e : Exception){
            ""
        }

        holder.bind.textView17.text = try {
            "${arrayData.total} ${
                MainApplication.context.getString(
                R.string.aed)}"
        }catch (e : Exception){
            ""
        }

        holder.bind.textView1754.text = try {
            arrayData.createdAt
        }catch (e : Exception){
            ""
        }

//        Picasso.get().load(arrayData.shop.image).into(holder.bind.imgShop)


        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }



    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemWalletBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}