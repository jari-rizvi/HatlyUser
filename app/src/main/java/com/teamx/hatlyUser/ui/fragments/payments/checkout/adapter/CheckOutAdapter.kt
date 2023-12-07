package com.teamx.hatlyUser.ui.fragments.payments.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemCheckoutBinding
import com.teamx.hatlyUser.ui.fragments.payments.checkout.model.Product
import java.util.ArrayList

class CheckOutAdapter(
    private val addressArrayList: ArrayList<Product>
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.textView16.text = try {
            arrayData.productName
        }catch (e : Exception){
            ""
        }

        holder.bind.textView18.text = try {
            arrayData.specialInstruction
        }catch (e : Exception){
            ""
        }

        holder.bind.textView166.text = try {
            "${arrayData.prize} ${
                MainApplication.context.getString(
                R.string.aed)}"
        }catch (e : Exception){
            ""
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemCheckoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}