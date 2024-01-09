package com.teamx.hatlyUser.ui.fragments.products.adapter.required

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemProductVariationRadioBinding
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.model.Option


class ProductVariationRequiredAdapter(
    private val addressArrayList: List<Option>,
    private val selectedIndex : Int,
    private val productPreviewInterface: ProductPreviewInterface
) : RecyclerView.Adapter<RequiredViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequiredViewHolder {
        return RequiredViewHolder(
//            ItemProductVariationRadioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemProductVariationRadioBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RequiredViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val arrayData = addressArrayList[position]

        val salesPrizeAmount = if (arrayData.salePrice == 0.0) "" else "${arrayData.salePrice} ${holder.itemView.context.getString(R.string.aed)}"

        holder.bind.textView167.text = try {
            salesPrizeAmount
        }catch (e : Exception){
            ""
        }

        if (arrayData.salePrice != 0.0){
            holder.bind.textView16.paintFlags = holder.bind.textView16.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        val prizeAmount = if (arrayData.price.toInt() == 0) holder.itemView.context.getString(R.string.free) else "${arrayData.price} ${holder.itemView.context.getString(R.string.aed)}"

        holder.bind.textView16.text = try {
            prizeAmount
        } catch (e: Exception) {
            "null"
        }


        holder.bind.radioButton.text = try {
            arrayData.name
        } catch (e: Exception) {
            "null"
        }

        holder.bind.radioButton.setOnClickListener {
            productPreviewInterface.clickRadioItem(-1,position)
        }


        holder.bind.radioButton.isChecked = position == selectedIndex



//        holder.itemView.setOnClickListener {
////            hatlyShopInterface.clickCategoryItem(position)
//            hatlyShopInterface.clickRadioItem(-1, position)
//        }

        holder.bind.radioButton.setOnCheckedChangeListener { btnView, isChecked ->
            // do exiting stuff
//            productPreviewInterface.clickCheckBoxItem(position)
//            notifyItemChanged(position)
        }



//        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        holder.bind.recRequired.layoutManager = layoutManager
//        val productVariationRequiredAdapter = ProductVariationRadioBtnAdapter(arrayData.options,arrayData.selectedIndex, object : ProductPreviewInterface {
//            override fun clickRadioItem(requiredVarBox: Int, radioProperties: Int) {
//                productPreviewInterface.clickRadioItem(position,radioProperties)
////                Log.d("clickCategoryItem", "clickCategoryItem: requiredVarBox: ${position} / radioProperties: ${radioProperties}")
//            }
//            override fun clickCheckBoxItem(optionalVeriation: Int) {
//
//            }
//
//            override fun clickFreBoughtItem(position: Int) {
//
//            }
//        })
//        holder.bind.recRequired.adapter = productVariationRequiredAdapter

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }

//    override fun clickshopItem(position: Int) {
//
//    }
//
//    override fun clickCategoryItem(position: Int) {
//        Log.d("clickCategoryItem", "clickCategoryItem: ${position}")
//
//
//        productPreviewInterface.clikRadioItem(getAdapterPosition(), position)
//    }
//
//    override fun clickMoreItem(position: Int) {
//
//    }


}

class RequiredViewHolder(private var binding: ItemProductVariationRadioBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}