package com.teamx.hatlyUser.ui.fragments.track

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.databinding.FragmentTrackBinding
import com.teamx.hatlyUser.ui.fragments.chat.adapter.ChatAdapter
import com.teamx.hatlyUser.ui.fragments.track.socket.MessageSocketClass
import com.teamx.hatlyUser.ui.fragments.track.socket.model.allChat.Doc
import com.teamx.hatlyUser.ui.fragments.track.socket.model.allChat.GetAllMessageData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrackFragment : BaseFragment<FragmentTrackBinding, TrackViewModel>(),
    MessageSocketClass.ReceiveSendMessageCallback, MessageSocketClass.GetAllMessageCallBack {

    override val layoutId: Int
        get() = R.layout.fragment_track
    override val viewModel: Class<TrackViewModel>
        get() = TrackViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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

        val bottomSheetCons: ConstraintLayout = view.findViewById(R.id.bottomSheetChat)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetCons)


        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.hatlyIcon1.setOnClickListener {
            showBottomSheetDialog(view)
            inpChat.setText("")

            MessageSocketClass.connect2(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGlmaWNhdGlvbiI6eyJpdiI6IjZiNjQ3NTMzNjkzODM3NjM2ODMyNmIzOTM1MzczODY0IiwiZW5jcnlwdGVkRGF0YSI6IjUxODQ0ZTQ2OTMzY2YzMDkyNTVhNDYxZDZmYTE1YTRjZDcwY2I0MmY4ZGI1YWM1MWMzMzlmODVlOTMyNDE3NmMifSwidW5pcXVlSWQiOiI0ZWYwZWM2NjdmNjVhZTE5ZTk4NmJlOWU4YTNlZjYiLCJpYXQiOjE2OTg3NjEyOTgsImV4cCI6MTAzMzg3NjEyOTh9.SePtKPAgeuYRY6byVwq5_p_nSMSy6uIGSmF5Krx4sx0",
                "6511befda128e070ad313243", this, this
            )

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

//            ${ NetworkCallPointsNest.DEVICE_TOKEN}

        }


//        MessageSocketClass.connect2(
//            "${NetworkCallPointsNest.DEVICE_TOKEN}",
//            "6511befda128e070ad313243", this, this
//        )

//        MessageSocketClass.connect2(
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGlmaWNhdGlvbiI6eyJpdiI6IjZiNjQ3NTMzNjkzODM3NjM2ODMyNmIzOTM1MzczODY0IiwiZW5jcnlwdGVkRGF0YSI6IjUxODQ0ZTQ2OTMzY2YzMDkyNTVhNDYxZDZmYTE1YTRjZDcwY2I0MmY4ZGI1YWM1MWMzMzlmODVlOTMyNDE3NmMifSwidW5pcXVlSWQiOiI0ZWYwZWM2NjdmNjVhZTE5ZTk4NmJlOWU4YTNlZjYiLCJpYXQiOjE2OTg3NjEyOTgsImV4cCI6MTAzMzg3NjEyOTh9.SePtKPAgeuYRY6byVwq5_p_nSMSy6uIGSmF5Krx4sx0",
//            "6511befda128e070ad313243", this, this
//        )

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // This method is called when the state of the bottom sheet changes.
                // You can react to different states here.
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.d("bottomSheetBehavior", "onStateChanged: STATE_EXPANDED")

                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.d("bottomSheetBehavior", "onStateChanged: STATE_COLLAPSED")
                        MessageSocketClass.disconnect()
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Log.d("bottomSheetBehavior", "onStateChanged: STATE_DRAGGING")
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.d("bottomSheetBehavior", "onStateChanged: STATE_HIDDEN")
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // This method is called when the bottom sheet is being dragged.
                // You can use this for animations or other effects based on the drag position.
            }
        })


    }

    private lateinit var chatArrayList: ArrayList<Doc>
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var recChat: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var inpChat: EditText

    private fun showBottomSheetDialog(view: View) {

        recChat = view.findViewById(R.id.recChat)
        val imgBackSheet = view.findViewById<ImageView>(R.id.imgBackSheet)
        val imgSend = view.findViewById<ImageView>(R.id.imgSend)
        inpChat = view.findViewById(R.id.inpChat)
        linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recChat.layoutManager = linearLayoutManager

        chatArrayList = ArrayList()

        chatAdapter = ChatAdapter(chatArrayList)
        recChat.adapter = chatAdapter

        imgSend.setOnClickListener {
            val text = inpChat.text.toString()
            if (text.isNotEmpty()) {
                MessageSocketClass.sendMessageTo(text, this)
            } else if (text.isNotBlank()) {
                MessageSocketClass.sendMessageTo(text, this)
            }
        }

        imgBackSheet?.setOnClickListener {
            Log.d("sdsdsdsdsd", "onStateChanged: click")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    override fun onGetAllMessage(getAllChatsData: GetAllMessageData) {
        GlobalScope.launch(Dispatchers.Main) {
//            delay(2000)
            Log.d("onGetAllMessage", "onStateChanged: click $getAllChatsData")
            chatArrayList.clear()
            getAllChatsData.docs.forEach {
                it.isUser = it.from == "64ca3762d1c8eed8afd363d9"
                chatArrayList.add(it)
//                chatAdapter.messageArrayList.add(it)
            }

            Log.d("chatArrayListS", "onGetAllMessage: ${chatArrayList.size}")
            chatAdapter.notifyDataSetChanged()
            val lastItemPosition = chatAdapter.itemCount - 1
            linearLayoutManager.scrollToPosition(lastItemPosition)
        }

    }

    override fun responseMessage() {
        Log.d("onGetAllMessage", "responseMessage")
    }

    override fun onGetReceiveMessage(getAllChatsData: Doc) {
        Log.d("onGetAllMessage", "onGetReceiveMessage $getAllChatsData")
        GlobalScope.launch(Dispatchers.Main) {


            getAllChatsData.isUser = getAllChatsData.from == "64ca3762d1c8eed8afd363d9"
            chatArrayList.add(getAllChatsData)
//        chatAdapter.notifyItemInserted(chatArrayList.size+1)
            inpChat.setText("")
            recChat.adapter?.notifyItemInserted(chatArrayList.size + 1)
            val lastItemPosition = chatAdapter.itemCount - 1
            linearLayoutManager.scrollToPosition(lastItemPosition)
        }
//        recChat.scrollToPosition(chatArrayList.size - 1)
    }

    override fun responseMessage2(str: String) {
        Log.d("onGetAllMessage", "responseMessage2 $str")
    }


}