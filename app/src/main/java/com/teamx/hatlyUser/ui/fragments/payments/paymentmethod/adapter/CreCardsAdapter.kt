package com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemCredCardsBinding
import com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.modelGetCards.PaymentMethod
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface

class CredCardsAdapter(
    private val addressArrayList: ArrayList<PaymentMethod>,
    private val productPreInterface: ProductPreviewInterface
) : RecyclerView.Adapter<CredCardsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CredCardsViewHolder {
        return CredCardsViewHolder(
            ItemCredCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: CredCardsViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        holder.bind.txtLogin12.isChecked = !arrayData.default

        holder.bind.txtLogin12.isEnabled = !arrayData.default

        holder.bind.txtCardName.text = try {
            arrayData.card.brand
        } catch (e: Exception) {
            "null"
        }

        holder.bind.txtCardDetail.text = try {
            "**** **** **** ${arrayData.card.last4} | ${arrayData.card.exp_month}/${arrayData.card.exp_year}"
        } catch (e: Exception) {
            "null"
        }

        holder.bind.txtLogin12.setOnClickListener {
            if (!arrayData.default) {
                productPreInterface.clickFreBoughtItem(position)
            }
        }

        holder.bind.txtLogin123.setOnClickListener {
            productPreInterface.clickCheckBoxItem(position)
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class CredCardsViewHolder(private var binding: ItemCredCardsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}