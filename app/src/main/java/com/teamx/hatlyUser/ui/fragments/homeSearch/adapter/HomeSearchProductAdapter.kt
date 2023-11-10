package com.teamx.hatlyUser.ui.fragments.homeSearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemHomeSearchProductBinding
import com.teamx.hatlyUser.ui.fragments.homeSearch.model.Item

class HomeSearchProductAdapter(
    private val addressArrayList: List<Item>
) : RecyclerView.Adapter<HomeSearchProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSearchProductViewHolder {
        return HomeSearchProductViewHolder(
            ItemHomeSearchProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HomeSearchProductViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.txtTitle.text = try {
            arrayData.name
        } catch (e: Exception) {
            ""
        }

        holder.bind.txtPrize.text = try {
            "${arrayData.price?:arrayData.minPrice} Aed"
        }catch (e : Exception){
            ""
        }

        Picasso.get().load(arrayData.images[0]).resize(500, 500).into(holder.bind.imgShop)

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HomeSearchProductViewHolder(private var binding: ItemHomeSearchProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}