package com.teamx.hatlyUser.ui.fragments.foods.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemReviewProductBinding

class ReviewProductAdapter(
    private val addressArrayList: ArrayList<String>
) : RecyclerView.Adapter<ReviewProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewProductViewHolder {
        return ReviewProductViewHolder(
            ItemReviewProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: ReviewProductViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class ReviewProductViewHolder(private var binding: ItemReviewProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}