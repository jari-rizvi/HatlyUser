package com.teamx.hatlyUser.ui.fragments.onboard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DemoCollectionAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, var fragmentList: ArrayList<Fragment>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {

        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}