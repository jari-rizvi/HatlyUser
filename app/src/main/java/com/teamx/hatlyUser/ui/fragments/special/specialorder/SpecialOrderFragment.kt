package com.teamx.hatlyUser.ui.fragments.special.specialorder

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentSpecialOrderBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.special.specialorder.adapter.SpecialOrderAdapter
import com.teamx.hatlyUser.ui.fragments.special.specialorder.model.DeliveredParcel
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SpecialOrderFragment : BaseFragment<FragmentSpecialOrderBinding, SpecialOrderViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_special_order
    override val viewModel: Class<SpecialOrderViewModel>
        get() = SpecialOrderViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var delieveredParcel: ArrayList<DeliveredParcel>

    var orderId = ""


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
            findNavController().navigate(R.id.action_specialOrderFragment_to_parcelDetailFragment)
        }

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recSpecial.layoutManager = layoutManager1

        delieveredParcel = ArrayList()

        val delieveredAdapter = SpecialOrderAdapter(delieveredParcel, this)
        mViewDataBinding.recSpecial.adapter = delieveredAdapter

        mViewDataBinding.txtLogin32.setOnClickListener {
            if (orderId.isNotEmpty()) {
                val bundle1 = Bundle()
                bundle1.putString("orderId", orderId)
                findNavController().navigate(R.id.action_specialOrderFragment_to_trackFragment)
            }
        }

        mViewModel.activeDeliever(false, 1, 10)

        mViewModel.activeDelieverResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }

                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        if (data.deliveredParcels.isNotEmpty()) {
                            delieveredParcel.clear()
                            delieveredParcel.addAll(data.deliveredParcels)
                            delieveredAdapter.notifyDataSetChanged()
                        } else {
                            mViewDataBinding.textView22545454.visibility = View.VISIBLE
                        }

                        if (data.activeParcels.isEmpty()) {
                            mViewDataBinding.constraintLayout6.visibility = View.GONE
                            mViewDataBinding.textView2254545544.visibility = View.VISIBLE
                            return@observe
                        } else {
                            mViewDataBinding.constraintLayout6.visibility = View.VISIBLE
                            mViewDataBinding.textView2254545544.visibility = View.GONE
                        }

                        Log.d("activeParcels", "onViewCreated: ${data.activeParcels}")

                        orderId = data.activeParcels[0]._id

                        mViewDataBinding.textView2223.text = try {
                            data.activeParcels[0].details.item
                        } catch (e: Exception) {
                            ""
                        }

                        mViewDataBinding.textView222.text = try {
                            "${getString(R.string.tracking_id)} ${data.activeParcels[0].trackingNumber}"
                        } catch (e: Exception) {
                            "null"
                        }

                        mViewDataBinding.textView22725.text = try {
                            data.activeParcels[0].pickup.address
                        } catch (e: Exception) {
                            "null"
                        }

                        mViewDataBinding.textView227925.text = try {
                            data.activeParcels[0].dropOff.address
                        } catch (e: Exception) {
                            "null"
                        }


                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (isAdded) {
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }


    }

    override fun clickshopItem(position: Int) {

    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}