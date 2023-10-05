package com.teamx.hatlyUser.ui.fragments.products.adapter.frequentlyBought

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemPopularBinding
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.model.Recommended

class RecommendedItemAdapter(
    private val addressArrayList: ArrayList<Recommended>,
    val productPreInterface : ProductPreviewInterface
) : RecyclerView.Adapter<FrequentlyBoughtViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrequentlyBoughtViewHolder {
        return FrequentlyBoughtViewHolder(
            ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: FrequentlyBoughtViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        holder.bind.txtTitle.text = try {
            arrayData.name
        } catch (e: Exception) {
            ""
        }

        holder.bind.txtPrize.text = try {
            "${arrayData.prize} Aed"
        } catch (e: Exception) {
            ""
        }

        Picasso.get().load(arrayData.images[0]).into(holder.bind.imgShop)


        holder.itemView.setOnClickListener {
            productPreInterface.clickFreBoughtItem(position)
        }


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class FrequentlyBoughtViewHolder(private var binding: ItemPopularBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}