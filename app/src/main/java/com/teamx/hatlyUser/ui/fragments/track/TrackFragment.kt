package com.teamx.hatlyUser.ui.fragments.track

import android.os.Bundle
import android.view.View
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentTrackBinding
import com.teamx.hatlyUser.ui.fragments.chat.ChatFragment
import com.teamx.hatlyUser.ui.fragments.chat.adapter.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TrackFragment : BaseFragment<FragmentTrackBinding, TrackViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_track
    override val viewModel: Class<TrackViewModel>
        get() = TrackViewModel::class.java
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

        mViewDataBinding.hatlyIcon1.setOnClickListener {
            val addPhotoBottomDialogFragment = ChatFragment()
            addPhotoBottomDialogFragment.show(requireActivity().supportFragmentManager, null)
//            showBottomSheetDialog()
        }

//        val onBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                Log.d("handleOnBackPressed", "handleOnBackPressed: back")
//                findNavController().popBackStack(R.id.homeFragment,false)
//            }
//        }
//
//        requireActivity().onBackPressedDispatcher.addCallback(
//            viewLifecycleOwner,
//            onBackPressedCallback
//        )


    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireActivity())
        bottomSheetDialog.setContentView(R.layout.fragment_chat)

        val recChat = bottomSheetDialog.findViewById<RecyclerView>(R.id.recChat)

        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        recChat!!.layoutManager = layoutManager2

        val itemClasses: ArrayList<Boolean> = ArrayList()

        itemClasses.add(true)
        itemClasses.add(false)
        itemClasses.add(true)
        itemClasses.add(false)
        itemClasses.add(true)
        itemClasses.add(false)
        itemClasses.add(true)
        itemClasses.add(false)
        itemClasses.add(true)
        itemClasses.add(false)
        itemClasses.add(false)
        itemClasses.add(true)
        itemClasses.add(true)
        itemClasses.add(true)
        itemClasses.add(false)
        itemClasses.add(false)
        itemClasses.add(false)
        itemClasses.add(false)
        itemClasses.add(false)
        itemClasses.add(true)
        itemClasses.add(true)
        itemClasses.add(true)
        itemClasses.add(false)
        itemClasses.add(true)
        itemClasses.add(true)

        val hatlyPopularAdapter = ChatAdapter(itemClasses)
        recChat.adapter = hatlyPopularAdapter

        if (!bottomSheetDialog.isShowing) {
            bottomSheetDialog.show()
        }
    }


}