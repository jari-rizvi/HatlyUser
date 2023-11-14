package com.teamx.hatlyUser.ui.fragments.products.adapter.frequentlyBought

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemPopularBinding
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.model.Recommended

class RecommendedItemAdapter(
    private val addressArrayList: ArrayList<Recommended>,
    private val productPreInterface : ProductPreviewInterface
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


        var textView26Price = ""
        var textView27Price = ""


        if (arrayData.productType == "simple") {

            textView26Price = if (arrayData.salePrice == 0.0) "" else "${arrayData.salePrice} Aed"

            textView27Price = "${arrayData.price} Aed"

            Log.d("FoodsShopProductAdapter", "onBindViewHolder: working $position")

            holder.bind.textView27.paintFlags = holder.bind.textView27.paintFlags

            if (arrayData.salePrice != 0.0) {
                holder.bind.textView27.paintFlags = holder.bind.textView27.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        } else {
            textView26Price = "${arrayData.minPrice} Aed"
            textView27Price = "${arrayData.maxPrice} Aed"
        }


        holder.bind.txtPrize.text = try {
            textView26Price
        } catch (e: Exception) {
            ""
        }

        holder.bind.textView27.text = try {
            textView27Price
        } catch (e: Exception) {
            ""
        }

        Picasso.get().load(arrayData.images[0]).resize(500,500).into(holder.bind.imgShop)


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