package com.teamx.hatlyUser.ui.fragments.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.databinding.ItemChatRiderBinding
import com.teamx.hatlyUser.databinding.ItemChatUserBinding
import kotlin.collections.ArrayList


class ChatAdapter(
    private val messageArrayList: ArrayList<Boolean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_USER = 1
    private val VIEW_TYPE_Rider = 2


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_Rider -> {
                MessageRiderViewHolder(
                    ItemChatRiderBinding.inflate(
                        LayoutInflater.from(viewGroup.context), viewGroup, false
                    )
                )
            }

            VIEW_TYPE_USER -> {
                MessageUserViewHolder(
                    ItemChatUserBinding.inflate(
                        LayoutInflater.from(viewGroup.context), viewGroup, false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid item type")
        }
    }


    override fun getItemCount(): Int {
        return messageArrayList.size
    }


    override fun getItemViewType(position: Int): Int {
        return when (messageArrayList[position]) {
            true -> VIEW_TYPE_USER
            false -> VIEW_TYPE_Rider
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messagesUser = messageArrayList[position]


        when (messagesUser) {
            true -> {


            }
            false -> {


            }

        }


    }
}


class MessageRiderViewHolder(binding: ItemChatRiderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val bind = binding

}

class MessageUserViewHolder(binding: ItemChatUserBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val bind = binding

}