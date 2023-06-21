package com.teamx.hatlyUser.ui.fragments.special.specialorder

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentSpecialOrderBinding
import com.teamx.hatlyUser.ui.fragments.special.specialorder.adapter.SpecialOrderAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
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
            findNavController().navigate(R.id.action_specialOrderFragment_to_sendParcelFragment)
        }

        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recSpecial.layoutManager = layoutManager1

        val itemClasses: ArrayList<String> = ArrayList()

        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")

        val adapter = SpecialOrderAdapter(itemClasses,this)
        mViewDataBinding.recSpecial.adapter = adapter


    }

    override fun clickshopItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}