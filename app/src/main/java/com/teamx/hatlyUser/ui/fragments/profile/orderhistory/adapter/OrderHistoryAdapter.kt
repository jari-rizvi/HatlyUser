package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemOrderHistoryBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Doc
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Shop
import com.teamx.hatlyUser.utils.TimeFormatter
import com.teamx.hatlyUser.utils.TimeFormatter.Companion.formatTimeDifference
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class OrderHistoryAdapter(
    private val addressArrayList: ArrayList<Doc>,
    private val hatlyShopInterface: HatlyShopInterface
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]


        holder.bind.txtTitle.text = try {
            arrayData.shop.name
        }catch (e : Exception){
            ""
        }

        holder.bind.txtStatus.text = try {
            arrayData.status
        }catch (e : Exception){
            ""
        }

        holder.bind.txtTime.text = try {
            formatTimeDifference(arrayData.createdAt)
        }catch (e : Exception){
            ""
        }

        Picasso.get().load(arrayData.shop.image).into(holder.bind.imgShop)


        holder.bind.txtDet.setOnClickListener {
            hatlyShopInterface.clickshopItem(position)
        }


    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun formatTimeDifference(timestamp: String): String {
//    val instant = Instant.parse(timestamp)
//    val now = Instant.now()
//    val duration = Duration.between(instant, now)
//
//    return when {
//        duration.toDays() > 2 -> {
//            // If more than 2 days, return the actual date
//            val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
//            localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//        }
//        duration.toDays() > 1 -> "${duration.toDays()} days ago"
//        duration.toDays() == 1L -> "1 day ago"
//        duration.toHours() > 1 -> "${duration.toHours()} hours ago"
//        duration.toHours() == 1L -> "1 hour ago"
//        duration.toMinutes() > 1 -> "${duration.toMinutes()} minutes ago"
//        duration.toMinutes() == 1L -> "1 minute ago"
//        else -> "just now"
//    }
//}

class HatlyPopularViewHolder(private var binding: ItemOrderHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}