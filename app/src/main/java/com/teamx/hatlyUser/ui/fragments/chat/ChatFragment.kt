package com.teamx.hatlyUser.ui.fragments.chat

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.ui.fragments.chat.adapter.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatFragment : BottomSheetDialogFragment() {

//    override val layoutId: Int
//        get() = R.layout.fragment_chat
//    override val viewModel: Class<ChatViewModel>
//        get() = ChatViewModel::class.java
//    override val bindingVariable: Int
//        get() = BR.viewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = inflater.inflate(R.layout.fragment_chat, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        options = navOptions {
//            anim {
//                enter = R.anim.enter_from_left
//                exit = R.anim.exit_to_left
//                popEnter = R.anim.nav_default_pop_enter_anim
//                popExit = R.anim.nav_default_pop_exit_anim
//            }
//        }

        val recChat = view.findViewById<RecyclerView>(R.id.recChat)

        val layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        recChat.layoutManager = layoutManager2

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

    }



}