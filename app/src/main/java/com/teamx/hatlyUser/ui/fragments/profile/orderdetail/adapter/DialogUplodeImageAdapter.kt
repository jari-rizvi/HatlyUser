package com.teamx.hatlyUser.ui.fragments.profile.orderdetail.adapter

import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.MainApplication.Companion.context
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.databinding.ItemShopCatBinding
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelCategory.Doc
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface


class DialogUplodeImageAdapter(
    private val foodsCategoryArrayList: ArrayList<Uri>,
    val hatlyShopInterface: HatlyShopInterface
) : RecyclerView.Adapter<DialogUplodeImageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DialogUplodeImageViewHolder {
        return DialogUplodeImageViewHolder(
            ItemShopCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: DialogUplodeImageViewHolder, position: Int) {

        val foodsCategory = foodsCategoryArrayList[position]



//        Picasso.get().load(foodsCategory).into(holder.bind.imgShop)

        Picasso.get()
            .load(foodsCategory)
            .resize(500, 500)  // Specify the dimensions you need
            .centerInside()
            .into(holder.bind.imgShop);

        holder.itemView.setOnClickListener {
            hatlyShopInterface.clickCategoryItem(position)
        }

    }

    override fun getItemCount(): Int {
        return foodsCategoryArrayList.size
    }
}

class DialogUplodeImageViewHolder(private var binding: ItemShopCatBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}