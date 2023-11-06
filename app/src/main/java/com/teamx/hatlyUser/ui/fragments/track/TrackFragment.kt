package com.teamx.hatlyUser.ui.fragments.track

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.TravelMode
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.databinding.FragmentTrackBinding
import com.teamx.hatlyUser.ui.fragments.chat.adapter.ChatAdapter
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.MessageSocketClass
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.model.allChat.Doc
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.model.allChat.GetAllMessageData
import com.teamx.hatlyUser.utils.LocationPermission
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrackFragment : BaseFragment<FragmentTrackBinding, TrackViewModel>(), OnMapReadyCallback,
    MessageSocketClass.ReceiveSendMessageCallback, MessageSocketClass.GetAllMessageCallBack {

    override val layoutId: Int
        get() = R.layout.fragment_track
    override val viewModel: Class<TrackViewModel>
        get() = TrackViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var orderId = ""

    var mapFragment: SupportMapFragment? = null
    private lateinit var googleMap: GoogleMap

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

        mapFragment =
            childFragmentManager.findFragmentById(R.id.mapTrackFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)


        val bundle = arguments

        if (bundle != null) {
            orderId = bundle.getString("orderId", "")
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
                "${NetworkCallPointsNest.DEVICE_TOKEN}",
                orderId, this, this
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


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(p0: GoogleMap) {
        if (LocationPermission.requestPermission(requireActivity())) {
            googleMap = p0

//            googleMap.uiSettings.isZoomControlsEnabled = false
//            googleMap.uiSettings.isScrollGesturesEnabled = false
//            googleMap.uiSettings.isRotateGesturesEnabled = false
//            googleMap.isMyLocationEnabled = false
//            googleMap.uiSettings.isTiltGesturesEnabled = false
//            googleMap.uiSettings.isZoomGesturesEnabled = false
//            googleMap.uiSettings.isMapToolbarEnabled = false

//            val location = LatLng(24.90141311636262, 67.1151442708337) // San Francisco coordinates
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))

//            googleMap.clear()
            createPollyLine()

        } else {
            if (isAdded) {
                mViewDataBinding.root.snackbar("Allow location")
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createPollyLine() {

        val destination = LatLng(24.897369355794208, 67.07753405615058)
        val origin = LatLng(24.90125984648241, 67.1152140082674)

        // Move the camera to the origin
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 12f))

        // Create a GeoApiContext with your API key
        val context =
            GeoApiContext.Builder().apiKey("AIzaSyAnLo0ejCEMH_cPgZaokWej4UdgyIIy5HI").build()

        // Request directions
        val directions = DirectionsApi.newRequest(context)
            .origin(com.google.maps.model.LatLng(origin.latitude, origin.longitude))
            .destination(com.google.maps.model.LatLng(destination.latitude, destination.longitude))
            .mode(TravelMode.DRIVING) // You can use other modes like walking, bicycling, etc.
            .await()

        Log.d("createPollyLine", "createPollyLine: ${directions.routes[0]}")

        // Convert Google Maps Directions API LatLng to Google Maps Android API LatLng
        val polyline = directions.routes[0].overviewPolyline.decodePath()
            .map { LatLng(it.lat, it.lng) }

        // Create a PolylineOptions and add the polyline to the map
        val polylineOptions = PolylineOptions()
            .addAll(polyline)
            .color(requireActivity().getColor(R.color.colorRed))
            .width(10f) // Line width

        googleMap.addPolyline(polylineOptions)
        animateCameraAlongPolyline(polyline)

    }


    private fun animateCameraAlongPolyline(polyline: List<LatLng>) {

        googleMap.setOnMapLoadedCallback {
            val startPosition = polyline.first()


            val endPosition = polyline.last()

//        val centerPosition = LatLng(
//            (startPosition.latitude + endPosition.latitude) / 2,
//            (startPosition.longitude + endPosition.longitude) / 2
//        )

            // Calculate appropriate zoom level to fit the entire polyline
            val bounds = LatLngBounds.builder()
                .include(startPosition)
                .include(endPosition)
                .build()

            val padding = 100 // Padding in pixels

            // Animate the camera to fit the bounds and center the polyline
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            googleMap.moveCamera(cameraUpdate)

            // Animate the camera to the center position with an appropriate zoom level
//        googleMap.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(centerPosition, 10f)
//        )
        }




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
                it.isUser = it.from == orderId
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


            getAllChatsData.isUser = getAllChatsData.from == orderId
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