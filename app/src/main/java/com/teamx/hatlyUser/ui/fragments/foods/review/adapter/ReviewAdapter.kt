package com.teamx.hatlyUser.ui.fragments.foods.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.MainApplication.Companion.context
import com.teamx.hatlyUser.databinding.ItemReviewBinding

class ReviewAdapter(
    private val addressArrayList: ArrayList<String>
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        holder.bind.recFoodTitle.layoutManager = layoutManager2

        val itemClasses: ArrayList<String> = ArrayList()

        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")

        val hatlyPopularAdapter = ReviewProductAdapter(itemClasses)
        holder.bind.recFoodTitle.adapter = hatlyPopularAdapter

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemReviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}