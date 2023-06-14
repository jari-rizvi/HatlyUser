package com.teamx.hatlyUser.ui.fragments.wallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemWalletBinding

class WalletAdapter(
    private val addressArrayList: ArrayList<String>
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]




    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemWalletBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}