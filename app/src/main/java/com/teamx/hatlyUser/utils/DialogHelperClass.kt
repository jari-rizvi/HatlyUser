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
import com.teamx.hatlyUser.MainApplication
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
                "$topPrice ${
                    MainApplication.context.getString(
                    R.string.aed)}"
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



        interface DialogCallBackSignIn {
            fun onSignInClick1()
            fun onSignUpClick1()
        }

        fun signUpLoginDialog(context: Context, dialogCallBack: DialogCallBackSignIn): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.signup_signin_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val signIn = dialog.findViewById<TextView>(R.id.signIn)
            val signUp = dialog.findViewById<TextView>(R.id.signUp)

            signUp.setOnClickListener {
                dialogCallBack.onSignUpClick1()
                dialog.dismiss()
            }
            signIn.setOnClickListener {
                dialogCallBack.onSignInClick1()
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }


        interface DialogProminentInterface {
            fun alloLocation()
            fun denyLocation()
        }

        fun prominentDialog(context: Context, dialogCallBack: DialogProminentInterface): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.permission_location_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val allowBtn = dialog.findViewById<TextView>(R.id.allowBtn)
            val denyBtn = dialog.findViewById<TextView>(R.id.denyBtn)

            denyBtn.setOnClickListener {
                dialogCallBack.denyLocation()
                dialog.dismiss()
            }

            allowBtn.setOnClickListener {
                dialogCallBack.alloLocation()
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }


        fun cancelOrderDialog(context: Context, dialogCallBack: DialogProminentInterface): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.cancel_order_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)
            val txtLogin123 = dialog.findViewById<TextView>(R.id.txtLogin123)

            txtLogin.setOnClickListener {
                dialogCallBack.alloLocation()
                dialog.dismiss()
            }

            txtLogin123.setOnClickListener {
                dialogCallBack.denyLocation()
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }

        fun shopOpen(context: Context, dialogCallBack: MultiProduct): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.shop_open_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )

            dialog.setCancelable(false)
            val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)

            txtLogin.setOnClickListener {
                dialogCallBack.prodRemove()
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }

    }
}