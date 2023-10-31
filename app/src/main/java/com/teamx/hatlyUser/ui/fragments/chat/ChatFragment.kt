package com.teamx.hatlyUser.ui.fragments.chat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
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

//    lateinit var itemClasses : ArrayList<Boolean>
//    lateinit var layoutManager2 : LinearLayoutManager
//    lateinit var chatAdapter : ChatAdapter
    lateinit var spin_kit : ProgressBar

    var isScrolling = false
    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0

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
        spin_kit = view.findViewById(R.id.spin_kit)

        var layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        recChat.layoutManager = layoutManager2

//        var itemClasses : ArrayList<Boolean> = ArrayList()
//
//        itemClasses.add(true)
//        itemClasses.add(false)
//        itemClasses.add(true)
//        itemClasses.add(false)
//        itemClasses.add(true)
//        itemClasses.add(false)
//        itemClasses.add(true)
//        itemClasses.add(false)
//        itemClasses.add(true)
//        itemClasses.add(false)
//        itemClasses.add(false)
//        itemClasses.add(true)
//        itemClasses.add(true)
//        itemClasses.add(true)
//        itemClasses.add(false)
//        itemClasses.add(false)
//        itemClasses.add(false)
//        itemClasses.add(false)
//        itemClasses.add(false)
//        itemClasses.add(true)
//        itemClasses.add(true)
//        itemClasses.add(true)
//        itemClasses.add(false)
//        itemClasses.add(true)
//        itemClasses.add(true)
//
//        val chatAdapter = ChatAdapter(itemClasses)
//        recChat.adapter = chatAdapter

//        recChat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isScrolling = true
//                }
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                currentItems = layoutManager2.childCount
//                totalItems = layoutManager2.itemCount
//                scrollOutItems = layoutManager2.findFirstVisibleItemPosition()
//
//                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
//                    isScrolling = false
//                    fetchData()
//                }
//            }
//        })

    }


//    private fun fetchData() {
//        spin_kit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                itemClasses.add(true)
//                itemClasses.add(false)
//                itemClasses.add(true)
//                itemClasses.add(false)
//                itemClasses.add(false)
//                itemClasses.add(true)
//                itemClasses.add(true)
//                itemClasses.add(false)
//                itemClasses.add(false)
//                itemClasses.add(true)
//                itemClasses.add(false)
//            }
//            spin_kit.visibility = View.GONE
//            chatAdapter.notifyDataSetChanged()
//        }, 5000)
//    }

}