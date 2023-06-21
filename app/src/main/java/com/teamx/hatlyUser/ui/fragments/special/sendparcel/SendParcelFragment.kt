package com.teamx.hatlyUser.ui.fragments.special.sendparcel

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentSendParcelBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SendParcelFragment : BaseFragment<FragmentSendParcelBinding, SendParcelViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_send_parcel
    override val viewModel: Class<SendParcelViewModel>
        get() = SendParcelViewModel::class.java
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
            findNavController().navigate(R.id.action_sendParcelFragment_to_parcelDetailFragment)
        }

        mViewDataBinding.constraintLayout8.setOnClickListener {
            mViewDataBinding.constraintLayout8.isActivated = true
            mViewDataBinding.constraintLayout7.isActivated = false
            mViewDataBinding.constraintLayout9.isActivated = false
            mViewDataBinding.constraintLayout10.isActivated = false

            mViewDataBinding.hatlyIcon.visibility = View.VISIBLE
            mViewDataBinding.hatlyIcon2.visibility = View.GONE
            mViewDataBinding.hatlyIcon3.visibility = View.GONE
            mViewDataBinding.hatlyIcon4.visibility = View.GONE

            mViewDataBinding.txtCycle.isChecked = true
            mViewDataBinding.txtBike.isChecked = false
            mViewDataBinding.txtCar.isChecked = false
            mViewDataBinding.txtTruck.isChecked = false
        }

        mViewDataBinding.constraintLayout7.setOnClickListener {
            mViewDataBinding.constraintLayout8.isActivated = false
            mViewDataBinding.constraintLayout7.isActivated = true
            mViewDataBinding.constraintLayout9.isActivated = false
            mViewDataBinding.constraintLayout10.isActivated = false

            mViewDataBinding.hatlyIcon.visibility = View.GONE
            mViewDataBinding.hatlyIcon2.visibility = View.VISIBLE
            mViewDataBinding.hatlyIcon3.visibility = View.GONE
            mViewDataBinding.hatlyIcon4.visibility = View.GONE

            mViewDataBinding.txtCycle.isChecked = false
            mViewDataBinding.txtBike.isChecked = true
            mViewDataBinding.txtCar.isChecked = false
            mViewDataBinding.txtTruck.isChecked = false
        }

        mViewDataBinding.constraintLayout9.setOnClickListener {
            mViewDataBinding.constraintLayout8.isActivated = false
            mViewDataBinding.constraintLayout7.isActivated = false
            mViewDataBinding.constraintLayout9.isActivated = true
            mViewDataBinding.constraintLayout10.isActivated = false

            mViewDataBinding.hatlyIcon.visibility = View.GONE
            mViewDataBinding.hatlyIcon2.visibility = View.GONE
            mViewDataBinding.hatlyIcon3.visibility = View.VISIBLE
            mViewDataBinding.hatlyIcon4.visibility = View.GONE

            mViewDataBinding.txtCycle.isChecked = false
            mViewDataBinding.txtBike.isChecked = false
            mViewDataBinding.txtCar.isChecked = true
            mViewDataBinding.txtTruck.isChecked = false
        }

        mViewDataBinding.constraintLayout10.setOnClickListener {
            mViewDataBinding.constraintLayout8.isActivated = false
            mViewDataBinding.constraintLayout7.isActivated = false
            mViewDataBinding.constraintLayout9.isActivated = false
            mViewDataBinding.constraintLayout10.isActivated = true

            mViewDataBinding.hatlyIcon.visibility = View.GONE
            mViewDataBinding.hatlyIcon2.visibility = View.GONE
            mViewDataBinding.hatlyIcon3.visibility = View.GONE
            mViewDataBinding.hatlyIcon4.visibility = View.VISIBLE

            mViewDataBinding.txtCycle.isChecked = false
            mViewDataBinding.txtBike.isChecked = false
            mViewDataBinding.txtCar.isChecked = false
            mViewDataBinding.txtTruck.isChecked = true
        }
    }


}