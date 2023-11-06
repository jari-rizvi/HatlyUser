package com.teamx.hatlyUser.ui.fragments.homeSearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.databinding.ItemHomeSearchBinding
import com.teamx.hatlyUser.ui.fragments.foods.review.adapter.ReviewProductAdapter
import com.teamx.hatlyUser.ui.fragments.homeSearch.model.Doc

class HomeSearchAdapter(
    private val addressArrayList: ArrayList<Doc>
) : RecyclerView.Adapter<HomeSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSearchViewHolder {
        return HomeSearchViewHolder(
            ItemHomeSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HomeSearchViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        Picasso.get().load(arrayData.image).resize(500,500).into(holder.bind.imgShop)

        holder.bind.textView38.text = try {
            arrayData.name
        }catch (e: Exception){
            ""
        }

        holder.bind.textView38.text = try {
            arrayData.name
        }catch (e: Exception){
            ""
        }

        holder.bind.textView37.text = try {
            "${arrayData.ratting.toFloat()} (${arrayData.totalReviews} reviews)"
        }catch (e: Exception){
            ""
        }

        holder.bind.txtDuration.text = try {
            "Delivery ${arrayData.delivery.value} ${arrayData.delivery.unit}"
        }catch (e: Exception){
            ""
        }

        holder.bind.materialRatingBar.rating = arrayData.ratting.toFloat()

        arrayData.items?.let {
            val layoutManager2 = LinearLayoutManager(MainApplication.context, LinearLayoutManager.HORIZONTAL, false)
            holder.bind.rechomeSearchPro.layoutManager = layoutManager2
            val hatlyPopularAdapter = HomeSearchProductAdapter(it)
            holder.bind.rechomeSearchPro.adapter = hatlyPopularAdapter
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HomeSearchViewHolder(private var binding: ItemHomeSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}