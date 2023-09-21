package com.teamx.hatlyUser.ui.fragments.products.adapter.required

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.MainApplication.Companion.context
import com.teamx.hatlyUser.databinding.ItemProductVariationRequiredBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.model.ShopVeriation


class ProductVariationRequiredAdapter(
    private val addressArrayList: ArrayList<ShopVeriation>,
    val productPreviewInterface: ProductPreviewInterface
) : RecyclerView.Adapter<RequiredViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequiredViewHolder {
        return RequiredViewHolder(
//            ItemProductVariationRadioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemProductVariationRequiredBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RequiredViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val arrayData = addressArrayList[position]


        holder.bind.txtReqTitle.text = try {
            arrayData.name
        } catch (e: Exception) {
            ""
        }



        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.bind.recRequired.layoutManager = layoutManager
        val productVariationRequiredAdapter = ProductVariationRadioBtnAdapter(arrayData.options,arrayData.selectedIndex, object : ProductPreviewInterface {
            override fun clickRadioItem(requiredVarBox: Int, radioProperties: Int) {
                productPreviewInterface.clickRadioItem(position,radioProperties)
//                Log.d("clickCategoryItem", "clickCategoryItem: requiredVarBox: ${position} / radioProperties: ${radioProperties}")
            }
            override fun clickCheckBoxItem(optionalVeriation: Int) {

            }
        })
        holder.bind.recRequired.adapter = productVariationRequiredAdapter

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

class RequiredViewHolder(private var binding: ItemProductVariationRequiredBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}