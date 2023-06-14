package com.teamx.hatlyUser.ui.fragments.setting.contactus

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentContactusBinding
import com.teamx.hatlyUser.databinding.FragmentSettingBinding
import com.teamx.hatlyUser.ui.fragments.setting.settings.SettingViewModel
import com.teamx.hatlyUser.utils.DialogHelperClass
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

        mViewDataBinding.txtLogin.setOnClickListener {
            DialogHelperClass.ContactDialog(requireActivity(), this)
        }



    }

    override fun onBackToHome() {
        findNavController().popBackStack()
    }


}