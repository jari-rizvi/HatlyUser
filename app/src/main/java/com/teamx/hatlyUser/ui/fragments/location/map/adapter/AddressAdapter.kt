package com.teamx.hatlyUser.ui.fragments.location.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.teamx.hatlyUser.databinding.ItemAddressListBinding
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface

class AddressListAdapter(
    private val addressArrayList: ArrayList<AutocompletePrediction>,
    val productPreInterface : ProductPreviewInterface
) : RecyclerView.Adapter<AddressListAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressListAdapterViewHolder {
        return AddressListAdapterViewHolder(
            ItemAddressListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: AddressListAdapterViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        holder.bind.txtTitle.text = try {
            arrayData.getFullText(null)
        } catch (e: Exception) {
            ""
        }

        holder.itemView.setOnClickListener {
            productPreInterface.clickFreBoughtItem(position)
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class AddressListAdapterViewHolder(private var binding: ItemAddressListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}