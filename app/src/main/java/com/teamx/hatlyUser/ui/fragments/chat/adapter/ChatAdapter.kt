package com.teamx.hatlyUser.ui.fragments.chat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.databinding.ItemChatRiderBinding
import com.teamx.hatlyUser.databinding.ItemChatUserBinding
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.model.allChat.Doc
import com.teamx.hatlyUser.utils.Helper
import kotlin.collections.ArrayList


class ChatAdapter(
    var messageArrayList: ArrayList<Doc>
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

            else -> {
                Log.d("getItemCount", "throw: IllegalArgumentException")
                throw IllegalArgumentException("Invalid item type")
            }
        }
    }


    override fun getItemCount(): Int {
        Log.d("getItemCount", "getItemCount: ${messageArrayList.size}")
        return messageArrayList.size
    }


    override fun getItemViewType(position: Int): Int {
        return when (messageArrayList[position].isUser) {
            true -> VIEW_TYPE_USER
            false -> VIEW_TYPE_Rider
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messagesUser = messageArrayList[position]


        when (messagesUser.isUser) {
            true -> {
                val holderUser = holder as MessageUserViewHolder

                if (Helper.isUrl(messagesUser.message)){
                    holderUser.bindUser.txtMessage.visibility = View.GONE
                    holder.bindUser.imgUserChat.visibility = View.VISIBLE
                    Picasso.get().load(messagesUser.message).resize(500, 500).into(holder.bindUser.imgUserChat)
                }else{
                    holder.bindUser.imgUserChat.visibility = View.GONE
                    holderUser.bindUser.txtMessage.visibility = View.VISIBLE
                    holderUser.bindUser.txtMessage.text = try {
                        messagesUser.message
                    } catch (e: Exception) {
                        ""
                    }
                }

//                holder.bindUser.imgUserChat.visibility = View.GONE
//                holderUser.bindUser.txtMessage.text = try {
//                    messagesUser.message
//                } catch (e: Exception) {
//                    ""
//                }


            }

            false -> {
                val holderRider = holder as MessageRiderViewHolder

                holderRider.bindRider.txtMessage.text = try {
                    messagesUser.message
                } catch (e: Exception) {
                    ""
                }
            }

        }


    }
}


class MessageRiderViewHolder(binding: ItemChatRiderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val bindRider = binding

}

class MessageUserViewHolder(binding: ItemChatUserBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val bindUser = binding

}