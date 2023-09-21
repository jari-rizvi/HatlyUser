package com.teamx.hatlyUser.ui.fragments.products.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.MainApplication.Companion.context
import com.teamx.hatlyUser.ui.fragments.products.adapter.optional.ProductVariationOptionalAdapter
import com.teamx.hatlyUser.ui.fragments.products.hatly.ItemClass
import com.teamx.hatlyUser.ui.fragments.products.hatly.ItemClass.Companion.LayoutOne
import com.teamx.hatlyUser.ui.fragments.products.hatly.ItemClass.Companion.LayoutTwo


class MultiViewVariationRadioAdapter(private val dataSet: ArrayList<ItemClass>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_1 = 0
        private const val VIEW_TYPE_2 = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_1 -> {
                val view = LayoutInflater.from(parent.context).inflate(com.teamx.hatlyUser.R.layout.item_product_variation_required, parent, false)
                RequiredViewHolder(view)
            }
            VIEW_TYPE_2 -> {
                val view = LayoutInflater.from(parent.context).inflate(com.teamx.hatlyUser.R.layout.item_product_variation_optional, parent, false)
                OptionalViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = dataSet[position]
        when (dataSet[position].viewType) {
            LayoutOne -> {
                val holderClass1 = holder as RequiredViewHolder

                val text: String = dataSet[position].text!!

                holderClass1.txtReqTitle.text = text
                holderClass1.txtRequired.text = text

                val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                holderClass1.recRequired.layoutManager = layoutManager
                val itemClasses: ArrayList<String> = ArrayList()
                itemClasses.add("")
                itemClasses.add("")
                itemClasses.add("")
                itemClasses.add("")

//                val productVariationRequiredAdapter = ProductVariationRequiredAdapter(itemClasses)
//                holderClass1.recRequired.adapter = productVariationRequiredAdapter

            }
            LayoutTwo -> {
                val holderClass2 = holder as OptionalViewHolder

                val text_one: String = dataSet[position].text_one!!
                val text_two: String = dataSet[position].text_two!!

                holderClass2.txtOptTitle.text = text_one
                holderClass2.txtOptional.text = text_one

                val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                holderClass2.recOpt.layoutManager = layoutManager2
                val itemClasses: ArrayList<String> = ArrayList()
                itemClasses.add("")
                itemClasses.add("")
                itemClasses.add("")

//                val productVariationOptionalAdapter = ProductVariationOptionalAdapter(itemClasses)
//                holderClass2.recOpt.adapter = productVariationOptionalAdapter

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

    internal class RequiredViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val txtReqTitle: TextView
        val txtRequired: TextView
        val recRequired: RecyclerView

        init {

            // Find the Views
            txtReqTitle = itemView.findViewById(com.teamx.hatlyUser.R.id.txtReqTitle)
            txtRequired = itemView.findViewById(com.teamx.hatlyUser.R.id.txtRequired)
            recRequired = itemView.findViewById(com.teamx.hatlyUser.R.id.recRequired)
        }

        // method to set the views that will
        // be used further in onBindViewHolder method.
//        fun setView(text: String) {
//            txtVarTitle.text = text
//        }
    }


    internal class OptionalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOptTitle: TextView
        val txtOptional: TextView
        val recOpt: RecyclerView


        init {
            txtOptTitle = itemView.findViewById(com.teamx.hatlyUser.R.id.txtOptTitle)
            txtOptional = itemView.findViewById(com.teamx.hatlyUser.R.id.txtOptional)
            recOpt = itemView.findViewById(com.teamx.hatlyUser.R.id.recOpt)
        }

//        fun setViews(
//            image: Int, textOne: String,
//            textTwo: String
//        ) {
//            icon.setImageResource(image)
//            text_one.text = textOne
//            text_two.text = textTwo
//        }
    }
}
