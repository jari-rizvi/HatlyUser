package com.teamx.hatlyUser.ui.fragments.payments.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemCartBinding
import com.teamx.hatlyUser.ui.fragments.payments.cart.interfaces.CartInterface
import com.teamx.hatlyUser.ui.fragments.payments.cart.model.Product

class CartAdapter(
    private val addressArrayList: ArrayList<Product>,
    val cartInterface: CartInterface
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.textView21.text = try {
            arrayData.productName
        } catch (e: Exception) {
            "null"
        }

        holder.bind.textView212.text = try {
            arrayData.specialInstruction
        } catch (e: Exception) {
            "null"
        }

        holder.bind.textView2122.text = try {
            "${arrayData.prize} Aed"
        } catch (e: Exception) {
            "null"
        }

        holder.bind.textView29.text = try {
            "${arrayData.quantity}"
        } catch (e: Exception) {
            "null"
        }

        holder.bind.imgIncreament.setOnClickListener {
            cartInterface.updateQuantity(position,arrayData.quantity + 1)
        }

        holder.bind.imgDereament.setOnClickListener {
            cartInterface.updateQuantity(position,arrayData.quantity - 1)
        }

        holder.bind.linearLayout4.setOnClickListener{
            cartInterface.removeCartItem(position)
        }



        Picasso.get().load(arrayData.image).resize(500,500).into(holder.bind.imgShop)

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}