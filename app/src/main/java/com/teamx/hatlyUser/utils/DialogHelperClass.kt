package com.teamx.hatlyUser.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.constraintlayout.widget.ConstraintLayout
import com.teamx.hatlyUser.R


class DialogHelperClass {
    interface DialogCallBack {
        fun onRemoveCartClicked()
        fun onRemoveBuyClicked()
    }

    companion object {

        fun loadingDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_layout_loading)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            return dialog
        }

        interface ReviewProduct {
            fun onSubmit(description: String, rating: Double)
            fun onCancel()
        }

        fun reviewDialog(context: Context,
                         startForResult : ActivityResultLauncher<String>, reviewProduct: ReviewProduct): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.permission_review_dialog)

            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )

            val imageView23 = dialog.findViewById<ImageView>(R.id.imageView23)

            val userDescription = dialog.findViewById<EditText>(R.id.userDescription)

            val materialRatingBar = dialog.findViewById<RatingBar>(R.id.materialRatingBar)
            val constraintLayout4 = dialog.findViewById<ConstraintLayout>(R.id.constraintLayout4)

            val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)

            imageView23.setOnClickListener {
                reviewProduct.onCancel()
                dialog.dismiss()
            }


            constraintLayout4.setOnClickListener {
                startForResult.launch("image/*")
            }

            txtLogin.setOnClickListener {
                dialog.dismiss()
                reviewProduct.onSubmit(userDescription.text.toString(), materialRatingBar.rating.toDouble())
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }

        interface ContactUs {
            fun onBackToHome()
        }

        fun ContactDialog(context: Context, contactUs: ContactUs): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.contactus_dialog)

            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );

            val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)

            txtLogin.setOnClickListener {
                contactUs.onBackToHome()
                dialog.dismiss()
            }



            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }



        fun wallettDialog(context: Context, topPrice : String, contactUs: ContactUs): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.wallet_dialog)

            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );

            val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)
            val txtTitle215545644 = dialog.findViewById<TextView>(R.id.txtTitle215545644)
            val txtDialogPrice = dialog.findViewById<TextView>(R.id.txtDialogPrice)

            txtDialogPrice.text = try {
                "$topPrice Aed"
            }catch (e : Exception){
                "null"
            }

            txtTitle215545644.setOnClickListener {
                dialog.dismiss()
            }

            txtLogin.setOnClickListener {
                contactUs.onBackToHome()
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }

        interface MultiProduct {
            fun prodRemove()
        }

        fun MultiProductDialog(context: Context, multiProduct: MultiProduct): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.multi_pro_dialog)

            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );

            val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)
            val txtLogin123 = dialog.findViewById<TextView>(R.id.txtLogin123)

            txtLogin.setOnClickListener {
                dialog.dismiss()
            }

            txtLogin123.setOnClickListener {
                multiProduct.prodRemove()
                dialog.dismiss()
            }



            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }


    }
}