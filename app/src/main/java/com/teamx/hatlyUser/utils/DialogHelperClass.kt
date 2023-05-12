package com.teamx.hatlyUser.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
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



    }
}