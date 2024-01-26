package com.teamx.hatlyUser.ui.fragments.products.adapter.required.multiAdapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.MainApplication.Companion.context
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.adapter.optional.ProductVariationOptionalAdapter
import com.teamx.hatlyUser.ui.fragments.products.adapter.required.ProductVariationRequiredAdapter
import com.teamx.hatlyUser.ui.fragments.products.model.Veriation

class MultiViewVariationRadioAdapter(
    private val dataSet: ArrayList<Veriation>,
    private val productPreviewInterface: ProductPreviewInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_1 = 0
        private const val VIEW_TYPE_2 = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_1 -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    com.teamx.hatlyUser.R.layout.item_product_variation_optional,
                    parent,
                    false
                )
                OptionalViewHolder(view)

            }

            VIEW_TYPE_2 -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    com.teamx.hatlyUser.R.layout.item_product_variation_required,
                    parent,
                    false
                )
                RequiredViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        when (dataSet[position].isMultiple) {
            true -> {
                val holderClass2 = holder as OptionalViewHolder

                holderClass2.txtOptTitle.text = try {
                    dataSet[position].title
                } catch (e: Exception) {
                    ""
                }

                if (dataSet[position].isRequired) {
                    holderClass2.txtOptional.text = holder.itemView.context.getString(R.string.required)
                } else {
                    holderClass2.txtOptional.text = holder.itemView.context.getString(R.string.optional)
                }

                holderClass2.txtOptional.isChecked = dataSet[position].isRequired

                val layoutManager2 =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                holderClass2.recOpt.layoutManager = layoutManager2

                val productVariationOptionalAdapter =
                    ProductVariationOptionalAdapter(dataSet[position].options, object :
                        ProductPreviewInterface {
                        override fun clickRadioItem(requiredVarBox: Int, radioProperties: Int) {
                            productPreviewInterface.clickRadioItem(position, radioProperties)
                        }
                        override fun clickCheckBoxItem(optionalVeriation: Int) {
                        }
                        override fun clickFreBoughtItem(position: Int) {
                        }
                    })
                holderClass2.recOpt.adapter = productVariationOptionalAdapter
            }

            false -> {
                val holderClass1 = holder as RequiredViewHolder

                holderClass1.txtReqTitle.text = try {
                    dataSet[position].title
                } catch (e: Exception) {
                    ""
                }

                if (dataSet[position].isRequired) {
                    holderClass1.txtRequired.text = holder.itemView.context.getString(R.string.required)
                } else {
                    holderClass1.txtRequired.text = "Optional"
                }

                holderClass1.txtRequired.isChecked = dataSet[position].isRequired

                val layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                holderClass1.recRequired.layoutManager = layoutManager

                val productVariationRequiredAdapter = ProductVariationRequiredAdapter(
                    dataSet[position].options,
                    dataSet[position].selectedIndex,
                    object : ProductPreviewInterface {
                        override fun clickRadioItem(requiredVarBox: Int, radioProperties: Int) {
                            productPreviewInterface.clickRadioItem(position, radioProperties)
                        }
                        override fun clickCheckBoxItem(optionalVeriation: Int) {

                        }
                        override fun clickFreBoughtItem(position: Int) {
                        }
                    })
                holderClass1.recRequired.adapter = productVariationRequiredAdapter

            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].isMultiple) {
            VIEW_TYPE_1
        } else {
            VIEW_TYPE_2
        }

    }

    internal class RequiredViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val txtReqTitle: TextView
        val txtRequired: CheckedTextView
        val recRequired: RecyclerView

        init {
            txtReqTitle = itemView.findViewById(com.teamx.hatlyUser.R.id.txtReqTitle)
            txtRequired = itemView.findViewById(com.teamx.hatlyUser.R.id.txtRequired)
            recRequired = itemView.findViewById(com.teamx.hatlyUser.R.id.recRequired)
        }
    }


    internal class OptionalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOptTitle: TextView
        val txtOptional: CheckedTextView
        val recOpt: RecyclerView


        init {
            txtOptTitle = itemView.findViewById(com.teamx.hatlyUser.R.id.txtOptTitle)
            txtOptional = itemView.findViewById(com.teamx.hatlyUser.R.id.txtOptional)
            recOpt = itemView.findViewById(com.teamx.hatlyUser.R.id.recOpt)
        }
    }
}
