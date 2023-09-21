package com.teamx.hatlyUser.ui.fragments.products.adapter.required

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemProductVariationRadioBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.ProductPreviewInterface

class ProductVariationRadioBtnAdapter(
    private val addressArrayList: List<String>,
    private val selectIndex: Int,
    val hatlyShopInterface: ProductPreviewInterface
) :
    RecyclerView.Adapter<RadioBtnViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioBtnViewHolder {
        return RadioBtnViewHolder(
            ItemProductVariationRadioBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
//            ItemProductVariationRequiredBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: RadioBtnViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        Log.d("clickCategoryItem", "clickCategoryItem: ${selectIndex}")


        holder.bind.radioButton.isChecked = position == selectIndex

        holder.bind.radioButton.text = try {
            arrayData
        } catch (e: Exception) {
            ""
        }

        holder.bind.radioButton.setOnClickListener {
            hatlyShopInterface.clickRadioItem(-1, position)
        }

//        holder.itemView.setOnClickListener {
////            hatlyShopInterface.clickCategoryItem(position)
//            hatlyShopInterface.clickRadioItem(-1, position)
//        }

        holder.bind.radioButton.setOnCheckedChangeListener { btnView, isChecked ->
            // do exiting stuff
//            productPreviewInterface.clickCheckBoxItem(position)
//            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class RadioBtnViewHolder(private var binding: ItemProductVariationRadioBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}