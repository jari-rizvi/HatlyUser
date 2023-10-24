package com.teamx.hatlyUser.ui.fragments.payments.paymentmethod

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentPaymentMethodBinding
import com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.adapter.CredCardsAdapter
import com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.modelGetCards.PaymentMethod
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException


@AndroidEntryPoint
class PaymentMethodFragment : BaseFragment<FragmentPaymentMethodBinding, PaymentMethodViewModel>(),
    ProductPreviewInterface {

    override val layoutId: Int
        get() = R.layout.fragment_payment_method
    override val viewModel: Class<PaymentMethodViewModel>
        get() = PaymentMethodViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var credCardsAdapter: CredCardsAdapter
    private lateinit var credCardsArrayList: ArrayList<PaymentMethod>

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

        credCardsArrayList = ArrayList()

        credCardsAdapter = CredCardsAdapter(credCardsArrayList, this)
        mViewDataBinding.recyCards.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recyCards.adapter = credCardsAdapter

        mViewModel.credCards()
        mViewModel.credCardsResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        credCardsArrayList.clear()
                        if (data.paymentMethod?.isNotEmpty() == true) {
                            credCardsArrayList.addAll(data.paymentMethod)
                        }
//                        if (data.default != null) {
//                            credCardsArrayList.add(data.default)
//                        }
                        credCardsAdapter.notifyDataSetChanged()
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }

        if (!mViewModel.defaultCredCardsResponse.hasActiveObservers()) {
            mViewModel.defaultCredCardsResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }

        if (!mViewModel.detachCredCardsResponse.hasActiveObservers()) {
            mViewModel.detachCredCardsResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }


        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    override fun clickRadioItem(requiredVarBox: Int, radioProperties: Int) {

    }

    var position : Int = -1

    override fun clickCheckBoxItem(detechPos: Int) {
        val credCardsModel = credCardsArrayList[detechPos]

        val params = JsonObject()

        try {
            params.addProperty("paymentMethod", credCardsModel.id)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        position = detechPos
        mViewModel.detachCredCards(params)
    }

    override fun clickFreBoughtItem(setAsDefaultPos: Int) {
        val credCardsModel = credCardsArrayList[setAsDefaultPos]

        val params = JsonObject()

        try {
            params.addProperty("paymentMethod", credCardsModel.id)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        position = setAsDefaultPos
        mViewModel.defaultCredCards(params)

        Log.d("credCardsModel", "clickFreBoughtItem: ${credCardsModel.id}")
    }


}