package com.teamx.hatlyUser.ui.fragments.setting.contactus

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentContactusBinding
import com.teamx.hatlyUser.utils.DialogHelperClass
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactusBinding, ContactUsViewModel>(),
    DialogHelperClass.Companion.ContactUs {

    override val layoutId: Int
        get() = R.layout.fragment_contactus
    override val viewModel: Class<ContactUsViewModel>
        get() = ContactUsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            if (isValidate()) {
                sendEmail("hatlykhalifa@gmail.com", "", "Name: ${mViewDataBinding.inpName.text.toString().trim()}\n\n${mViewDataBinding.inpMessage.text.toString().trim()}")
            }
        }
    }

    private fun isValidate(): Boolean {
        if (mViewDataBinding.inpName.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_your_name))
            return false
        }

        if (mViewDataBinding.inpMobile.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_your_mobile_number))
            return false
        }

        if (mViewDataBinding.inpEmail.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_you_email_address))
            return false
        }

        if (mViewDataBinding.inpMessage.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_message))
            return false
        }
        subscribeToNetworkLiveData()
        return true
    }

    override fun onBackToHome() {
        findNavController().popBackStack()
    }


    var REQUEST_CODE_EMAIL = 100


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EMAIL) {
            if (resultCode == RESULT_OK) {
                DialogHelperClass.ContactDialog(requireActivity(), this)
            } else {
                mViewDataBinding.root.snackbar("Email is not send!")
            }
        }
    }

    private fun sendEmail(to: String, subject: String, body: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:") // Only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)

        startActivityForResult(emailIntent, REQUEST_CODE_EMAIL);
    }


}