package com.teamx.hatlyUser.ui.fragments.products.adapter.optional

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemProductVariationCheckBinding
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.model.Option


class ProductVariationOptionalAdapter(
    private val addressArrayList: List<Option>,
    private val productPreviewInterface: ProductPreviewInterface
) : RecyclerView.Adapter<OptionalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionalViewHolder {
        return OptionalViewHolder(
//            ItemProductVariationOptionalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemProductVariationCheckBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: OptionalViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

//        holder.bind.radioButton.isChecked = arrayData.isSelected
        holder.bind.radioButton.isChecked = false

        holder.bind.radioButton.text = try {
            arrayData.name
        } catch (e: Exception) {
            "null"
        }

        val salesPrizeAmount = if (arrayData.salePrice == 0.0) "" else "${arrayData.salePrice} Aed"

        holder.bind.textView167.text = try {
            salesPrizeAmount
        }catch (e : Exception){
            ""
        }

        val prizeAmount = if (arrayData.price.toInt() == 0) "Free" else "${arrayData.price} Aed"

        holder.bind.textView16.text = try {
            prizeAmount
        } catch (e: Exception) {
            ""
        }

//        holder.itemView.setOnClickListener {
//            holder.bind.radioButton.isChecked = !arrayData.isSelected
//            productPreviewInterface.clickCheckBoxItem(position)
////            notifyItemChanged(position)
//        }

        holder.bind.radioButton.setOnClickListener {
//            holder.bind.radioButton.isChecked = !arrayData.isSelected
            productPreviewInterface.clickRadioItem(-1,position)
        }

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

class OptionalViewHolder(private var binding: ItemProductVariationCheckBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}