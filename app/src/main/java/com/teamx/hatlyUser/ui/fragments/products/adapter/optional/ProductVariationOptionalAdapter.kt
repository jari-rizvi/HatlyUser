package com.teamx.hatlyUser.ui.fragments.products.adapter.optional

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemProductVariationCheckBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.model.OptionalVeriaton


class ProductVariationOptionalAdapter(
    private val addressArrayList: ArrayList<OptionalVeriaton>,
    val productPreviewInterface: ProductPreviewInterface
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

        holder.bind.radioButton.isChecked = arrayData.isSelected

        holder.bind.radioButton.text = try {
            arrayData.name
        } catch (e: Exception) {
            ""
        }
        holder.bind.textView16.text = try {
            "${arrayData.prize} Aed"
        } catch (e: Exception) {
            ""
        }

//        holder.itemView.setOnClickListener {
//            holder.bind.radioButton.isChecked = !arrayData.isSelected
//            productPreviewInterface.clickCheckBoxItem(position)
////            notifyItemChanged(position)
//        }

        holder.bind.radioButton.setOnClickListener {
            holder.bind.radioButton.isChecked = !arrayData.isSelected
            productPreviewInterface.clickCheckBoxItem(position)
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