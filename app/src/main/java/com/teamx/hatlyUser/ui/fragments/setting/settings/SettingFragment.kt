package com.teamx.hatlyUser.ui.fragments.setting.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_setting
    override val viewModel: Class<SettingViewModel>
        get() = SettingViewModel::class.java
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

        mViewDataBinding.textView22.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_contactUsFragment)
        }

        mViewDataBinding.textView2222.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data =
//                Uri.parse("https://sites.google.com/view/zues-privacy-policy/home")
                Uri.parse("https://sites.google.com/view/privacypolicyforhatlyuser/home")
            startActivity(openURL)
        }

        mViewDataBinding.textView22322.setOnClickListener {

        }

        mViewDataBinding.textView223242.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data =
//                Uri.parse("https://sites.google.com/view/zues-privacy-policy/home")
                Uri.parse("https://sites.google.com/view/termsandconditionsforhatly/home")
            startActivity(openURL)
        }

        mViewDataBinding.swOnOff.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->

//            mViewDataBinding.swOnOff.isChecked = !isChecked

        })

    }


}