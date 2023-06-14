package com.teamx.hatlyUser.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
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
            fun onSubmit()
            fun onCancel()
        }

        fun reviewDialog(context: Context, reviewProduct: ReviewProduct): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.permission_review_dialog)

            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

            val imageView23 = dialog.findViewById<ImageView>(R.id.imageView23)

            val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)

            imageView23.setOnClickListener {
                reviewProduct.onCancel()
                dialog.dismiss()
            }

            txtLogin.setOnClickListener {
                dialog.dismiss()
                reviewProduct.onSubmit()
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

            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

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



    }
}