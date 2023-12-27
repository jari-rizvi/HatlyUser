package com.teamx.hatlyUser.ui.fragments.wallet.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemWalletBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.wallet.model.transaction.Doc

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


        holder.bind.textView175544.text = try {
            arrayData.`for`.capitalize()
        }catch (e:Exception){
            "null"
        }

        var amount = ""

        if (arrayData.change == "increment"){
            holder.bind.textView17.setTextColor(Color.parseColor("#1ED860"))
            amount = "+ ${arrayData.totalAmount}"
        }else if (arrayData.change == "decrement"){
            holder.bind.textView17.setTextColor(Color.parseColor("#EA1B25"))
            amount = "- ${arrayData.totalAmount}"
        }

        holder.bind.textView17.text = try {
            "${amount} ${
                MainApplication.context.getString(
                R.string.aed)}"
        }catch (e : Exception){
            "null"
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