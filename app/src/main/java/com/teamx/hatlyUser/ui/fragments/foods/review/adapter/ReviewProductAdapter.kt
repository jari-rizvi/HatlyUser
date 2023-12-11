package com.teamx.hatlyUser.ui.fragments.foods.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemReviewProductBinding

class ReviewProductAdapter(
    private val addressArrayList: List<String>
) : RecyclerView.Adapter<ReviewProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewProductViewHolder {
        return ReviewProductViewHolder(
            ItemReviewProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: ReviewProductViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        Picasso.get().load(arrayData).placeholder(R.drawable.hatly_splash_logo_space).error(R.drawable.hatly_splash_logo_space).resize(500,500).into(holder.bind.imgShop)

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class ReviewProductViewHolder(private var binding: ItemReviewProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}