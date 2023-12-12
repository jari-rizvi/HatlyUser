package com.teamx.hatlyUser.ui.fragments.specialOrderDetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.navOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentSpecialOrderDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialOrderDetailFragment : BaseFragment<FragmentSpecialOrderDetailBinding, SpecialOrderDetailViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_special_order_detail
    override val viewModel: Class<SpecialOrderDetailViewModel>
        get() = SpecialOrderDetailViewModel::class.java
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

        sharedViewModel.parcelOrderHistory.observe(requireActivity()) { historyModel ->
            mViewDataBinding.txtTitle477.text = try {
                historyModel.trackingNumber
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle4545.text = try {
                historyModel.details.item
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle45459845.text = try {
                "${historyModel.details.qty}"
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle4545984325.text = try {
                "${historyModel.senderLocation.location.address}"
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle454598488778325.text = try {
                "${historyModel.senderLocation.phoneNumber}"
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle4545977484325.text = try {
                "${historyModel.receiverLocation.location.address}"
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle4545984sd88778325.text = try {
                "${historyModel.receiverLocation.phoneNumber}"
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle5544459898544.text = try {
                "${historyModel.fare}"
            }catch (e : Exception){
                ""
            }
        }

    }



}