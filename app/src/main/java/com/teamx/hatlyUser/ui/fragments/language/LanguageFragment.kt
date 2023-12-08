package com.teamx.hatlyUser.ui.fragments.language

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentLanguageBinding
import com.teamx.hatlyUser.localization.LocaleManager
import com.teamx.hatlyUser.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding, LanguageViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_language
    override val viewModel: Class<LanguageViewModel>
        get() = LanguageViewModel::class.java
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

        if (PrefHelper.getInstance(requireContext()).langType!! == LocaleManager.LANGUAGE_ENGLISH) {
            mViewDataBinding.radioEnglish.isChecked = true
        } else {
            mViewDataBinding.radioArabic.isChecked = true
        }

        mViewDataBinding.radioEnglish.setOnClickListener {
            LocaleManager(requireContext()).setNewLocale(
                requireContext(), LocaleManager.LANGUAGE_ENGLISH
            )
            PrefHelper.getInstance(requireContext()).saveLANGTYPE(LocaleManager.LANGUAGE_ENGLISH)


            Handler(Looper.getMainLooper()).post {
                val intent = requireActivity().intent
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
                )
                requireActivity().overridePendingTransition(0, 0)
                requireActivity().finish()
                requireActivity().overridePendingTransition(0, 0)
                startActivity(intent)
            }

        }


        mViewDataBinding.radioArabic.setOnClickListener {
            LocaleManager(requireContext()).setNewLocale(
                requireContext(), LocaleManager.LANGUAGE_ARABIC
            )
            PrefHelper.getInstance(requireContext()).saveLANGTYPE(LocaleManager.LANGUAGE_ARABIC)

            Handler(Looper.getMainLooper()).post {
                val intent = requireActivity().intent
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
                )
                requireActivity().overridePendingTransition(0, 0)
                requireActivity().finish()
                requireActivity().overridePendingTransition(0, 0)
                startActivity(intent)
            }
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }


}