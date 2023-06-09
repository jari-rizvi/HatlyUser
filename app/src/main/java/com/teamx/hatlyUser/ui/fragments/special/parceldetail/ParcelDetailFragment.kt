package com.teamx.hatlyUser.ui.fragments.special.parceldetail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentParcelDetailBinding
import com.teamx.hatlyUser.ui.fragments.special.sendparcel.SendParcelViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ParcelDetailFragment : BaseFragment<FragmentParcelDetailBinding, ParcelDetailViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_parcel_detail
    override val viewModel: Class<ParcelDetailViewModel>
        get() = ParcelDetailViewModel::class.java
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


    }


}