package com.teamx.hatlyUser.ui.fragments.transactiondetail

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentTransactionDetailBinding
import com.teamx.hatlyUser.databinding.FragmentWalletBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.wallet.WalletViewModel
import com.teamx.hatlyUser.ui.fragments.wallet.adapter.WalletAdapter
import com.teamx.hatlyUser.ui.fragments.wallet.model.transaction.Doc
import com.teamx.hatlyUser.utils.TimeFormatter
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailFragment :
    BaseFragment<FragmentTransactionDetailBinding, WalletViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_transaction_detail
    override val viewModel: Class<WalletViewModel>
        get() = WalletViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    @RequiresApi(Build.VERSION_CODES.O)
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

        sharedViewModel.transactionDetail.observe(requireActivity()) { data ->

            when (data.payBy) {
                "wallet" -> {
                    mViewDataBinding.txtTitle114455.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.paid_with_wallet,
                        0,
                        0,
                        0
                    )
                }

                "STRIPE" -> {
                    mViewDataBinding.txtTitle114455.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.paid_with_card,
                        0,
                        0,
                        0
                    )
                }
                "cash" -> {
                    mViewDataBinding.txtTitle114455.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.paid_with_cash,
                        0,
                        0,
                        0
                    )
                }
            }

            mViewDataBinding.txtTitle1141.text = try {
                data.status.capitalize()
            } catch (e: Exception) {
                "null"
            }

            mViewDataBinding.txtTitle111545.text = try {
                data.id
            } catch (e: Exception) {
                "null"
            }

            mViewDataBinding.txtAddress.text = try {
                data.`for`.capitalize()
            } catch (e: Exception) {
                "null"
            }

            mViewDataBinding.txtTitle1113233.text = try {
                "${data.amount}"
            } catch (e: Exception) {
                "null"
            }

            mViewDataBinding.txtTitle114455.text = try {
                data.payBy.capitalize()
            }catch (e : Exception){
                "null"
            }

        }


    }


}