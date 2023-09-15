package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemShopCatBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.categoryModel.Doc

class HatlyShopCatAdapter(
    private val addressArrayList: ArrayList<Doc>,
    val hatlyShopInterface: HatlyShopInterface
) :
    RecyclerView.Adapter<AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemShopCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        if (arrayData.isShowMore) {
            holder.bind.imgMore.visibility = View.VISIBLE
            holder.bind.imgShop.visibility = View.GONE
            holder.bind.txtTitle.text = arrayData.name
        } else {
            holder.bind.imgMore.visibility = View.GONE
            holder.bind.imgShop.visibility = View.VISIBLE


            holder.bind.txtTitle.text = try {
                arrayData.name
            } catch (e: java.lang.Exception) {
                ""
            }

        Picasso.get().load(arrayData.image).into(holder.bind.imgShop)


            holder.itemView.setOnClickListener {
                hatlyShopInterface.clickshopItem(position)
            }
        }

        holder.bind.imgMore.setOnClickListener {
            hatlyShopInterface.clickMoreItem(position)
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class AddressViewHolder(private var binding: ItemShopCatBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}