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
import com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.model.Doc
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

    lateinit var delieveredParcel: ArrayList<Doc>

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


        mViewDataBinding.viewAll.setOnClickListener {
            findNavController().navigate(R.id.action_specialOrderFragment_to_specialOrderHistoryFragment)
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

        mViewModel.allParcel("delivered", 10, 1)

        mViewModel.allParcelResponse.observe(requireActivity()) {
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

                        if (data.docs.isNotEmpty()) {
                            delieveredParcel.clear()
                            delieveredParcel.addAll(data.docs)
                            delieveredAdapter.notifyDataSetChanged()
                        }

                        if (data.docs.isNotEmpty()) {
                            mViewDataBinding.recSpecial.visibility = View.VISIBLE
                            mViewDataBinding.textView22545454.visibility = View.GONE
                        }else{
                            mViewDataBinding.recSpecial.visibility = View.GONE
                            mViewDataBinding.textView22545454.visibility = View.VISIBLE
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

        mViewModel.activeDeliever()

        mViewModel.activeResponse.observe(requireActivity()) {
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

//                        Log.d("activeParcels", "onViewCreated: ${data.activeParcels}")

                        Log.d("activeResponse", "onViewCreated: ${data}")
                        Log.d("activeResponse", "onViewCreated: ${data.trackingNumber}")

                        if (data._id != null) {
                            mViewDataBinding.constraintLayout6.visibility = View.VISIBLE
                            mViewDataBinding.textView2254545544.visibility = View.GONE
                        } else {
                            mViewDataBinding.constraintLayout6.visibility = View.GONE
                            mViewDataBinding.textView2254545544.visibility = View.VISIBLE
                            return@observe
                        }

                        orderId = data._id

                        mViewDataBinding.textView2223.text = try {
                            data.details.item
                        } catch (e: Exception) {
                            ""
                        }

                        mViewDataBinding.textView222.text = try {
                            "${getString(R.string.tracking_id)} ${data.trackingNumber}"
                        } catch (e: Exception) {
                            "null"
                        }

                        mViewDataBinding.textView22725.text = try {
                            data.senderLocation.location.address
                        } catch (e: Exception) {
                            "null"
                        }

                        mViewDataBinding.textView227925.text = try {
                            data.receiverLocation.location.address
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
        val parcelModel = delieveredParcel[position]

        sharedViewModel.setParcelOrderHistory(parcelModel)
        findNavController().navigate(R.id.action_specialOrderFragment_to_specialOrderDetailFragment)
    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}