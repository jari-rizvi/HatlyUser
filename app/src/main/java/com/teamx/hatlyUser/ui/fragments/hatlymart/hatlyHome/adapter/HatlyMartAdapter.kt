package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.ui.fragments.products.hatly.ItemClass
import com.teamx.hatlyUser.ui.fragments.products.hatly.ItemClass.Companion.LayoutOne
import com.teamx.hatlyUser.ui.fragments.products.hatly.ItemClass.Companion.LayoutTwo


class HatlyMartAdapter(private val dataSet: List<ItemClass>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_1 = 0
        private const val VIEW_TYPE_2 = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_1 -> {
                val view = LayoutInflater.from(parent.context).inflate(com.teamx.hatlyUser.R.layout.item_shop_by_cat, parent, false)
                LayoutOneViewHolder(view)
            }
            VIEW_TYPE_2 -> {
                val view = LayoutInflater.from(parent.context).inflate(com.teamx.hatlyUser.R.layout.item_layout_2, parent, false)
                LayoutTwoViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = dataSet[position]
        when (dataSet[position].viewType) {
            LayoutOne -> {
                val holderClass1 = holder as LayoutOneViewHolder
                val text: String = dataSet[position].text!!
                holderClass1.setView(text)
                holderClass1.linearLayout.setOnClickListener { view ->
                    Toast.makeText(view.context, "Hello from Layout One!", Toast.LENGTH_SHORT).show()
                }
            }
            LayoutTwo -> {
                val holderClass2 = holder as LayoutTwoViewHolder

                val image: Int = dataSet[position].geticon()
                val text_one: String = dataSet[position].text_one!!
                val text_two: String = dataSet[position].text_two!!
                holderClass2.setViews(image, text_one, text_two)
                holderClass2.linearLayout.setOnClickListener { view ->
                    Toast.makeText(view.context, "Hello from Layout Two!", Toast.LENGTH_SHORT).show()
                }
            }
            else -> return
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].viewType
    }

    internal class LayoutOneViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val textview: TextView
        val linearLayout: LinearLayout

        init {

            // Find the Views
            textview = itemView.findViewById(com.teamx.hatlyUser.R.id.text)
            linearLayout = itemView.findViewById(com.teamx.hatlyUser.R.id.linearlayout)
        }

        // method to set the views that will
        // be used further in onBindViewHolder method.
        fun setView(text: String) {
            textview.text = text
        }
    }


    internal class LayoutTwoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView
        private val text_one: TextView
        private val text_two: TextView
        val linearLayout: LinearLayout

        init {
            icon = itemView.findViewById(com.teamx.hatlyUser.R.id.image)
            text_one = itemView.findViewById(com.teamx.hatlyUser.R.id.text_one)
            text_two = itemView.findViewById(com.teamx.hatlyUser.R.id.text_two)
            linearLayout = itemView.findViewById(com.teamx.hatlyUser.R.id.linearlayout)
        }

        fun setViews(
            image: Int, textOne: String,
            textTwo: String
        ) {
            icon.setImageResource(image)
            text_one.text = textOne
            text_two.text = textTwo
        }
    }
}
