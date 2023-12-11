package com.teamx.hatlyUser.ui.fragments.foods.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.MainApplication.Companion.context
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemReviewBinding
import com.teamx.hatlyUser.ui.fragments.foods.review.modelReviewList.Doc

class ReviewAdapter(
    private val addressArrayList: ArrayList<Doc>
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        holder.bind.textView38.text = try {
            arrayData.name
        }catch (e : Exception){
            ""
        }

        holder.bind.textView374.text = try {
            arrayData.description
        }catch (e : Exception){
            ""
        }

        holder.bind.textView37.text = try {
            arrayData.createdAt
        }catch (e : Exception){
            ""
        }

        Picasso.get().load(arrayData.profileImage).placeholder(R.drawable.hatly_splash_logo_space).error(
            R.drawable.hatly_splash_logo_space).resize(500,500).into(holder.bind.hatlyIcon)

        holder.bind.materialRatingBar.rating = arrayData.ratting.toFloat()

        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.bind.recFoodTitle.layoutManager = layoutManager2
        val hatlyPopularAdapter = ReviewProductAdapter(arrayData.images)
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