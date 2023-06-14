package com.teamx.hatlyUser.ui.fragments.profile.reviewsubmit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentReviewSubmitedBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReviewSubmitedFragment : BaseFragment<FragmentReviewSubmitedBinding, ReviewSubmitedViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_review_submited
    override val viewModel: Class<ReviewSubmitedViewModel>
        get() = ReviewSubmitedViewModel::class.java
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

        mViewDataBinding.txtLogin1.setOnClickListener {
            findNavController().popBackStack()
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("handleOnBackPressed", "handleOnBackPressed: back")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )



    }


}