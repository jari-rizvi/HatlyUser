package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemShopCatBinding
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelCategory.Doc
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import java.util.ArrayList

class FoodHomeCategoryAdapter(
    private val foodsCategoryArrayList: ArrayList<Doc>,
    val hatlyShopInterface: HatlyShopInterface
) : RecyclerView.Adapter<FoodHomeTitleAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodHomeTitleAdapterViewHolder {
        return FoodHomeTitleAdapterViewHolder(
            ItemShopCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: FoodHomeTitleAdapterViewHolder, position: Int) {

        val foodsCategory = foodsCategoryArrayList[position]

        holder.bind.txtTitle.text = try {
            foodsCategory.title
        } catch (e: Exception) {
            ""
        }

        Picasso.get().load(foodsCategory.image).into(holder.bind.imgShop)

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickCategoryItem(position)
        }

    }

    override fun getItemCount(): Int {
        return foodsCategoryArrayList.size
    }
}

class FoodHomeTitleAdapterViewHolder(private var binding: ItemShopCatBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}