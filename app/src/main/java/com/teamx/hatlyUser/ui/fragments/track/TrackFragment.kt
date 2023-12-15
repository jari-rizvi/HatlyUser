package com.teamx.hatlyUser.ui.fragments.track


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.TravelMode
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentTrackBinding
import com.teamx.hatlyUser.ui.fragments.chat.adapter.ChatAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.adapter.DialogUplodeImageAdapter
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.MessageSocketClass
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.model.allChat.Doc
import com.teamx.hatlyUser.ui.fragments.track.socket.chat.model.allChat.GetAllMessageData
import com.teamx.hatlyUser.ui.fragments.track.socket.track.TrackSocketClass
import com.teamx.hatlyUser.ui.fragments.track.socket.track.model.rider.TrackRiderModel
import com.teamx.hatlyUser.ui.fragments.track.socket.track.model.shop.TrackShopModel
import com.teamx.hatlyUser.utils.LocationPermission
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class TrackFragment : BaseFragment<FragmentTrackBinding, TrackViewModel>(), OnMapReadyCallback,
    MessageSocketClass.ReceiveSendMessageCallback, MessageSocketClass.GetAllMessageCallBack,
    TrackSocketClass.GetTrackDataCallBack, HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_track
    override val viewModel: Class<TrackViewModel>
        get() = TrackViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var orderId = ""
    private var userId = ""

    var mapFragment: SupportMapFragment? = null
    private lateinit var googleMap: GoogleMap

    lateinit var origin: LatLng

    private var isChatOpen = false

    var phoneNumber = ""

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

        val userData = PrefHelper.getInstance(requireActivity()).getUserData()

        if (userData != null) {
            userId = userData._id
        }

        sharedViewModel.setlocationmodel(userData?.location)

        sharedViewModel.locationmodel.observe(requireActivity()) { locationModel ->
            Log.d("onTrackFragment", "onViewCreated: ${locationModel.lat} , ${locationModel.lng}")
            origin = LatLng(locationModel.lat, locationModel.lng)
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

        mViewDataBinding.hatlyIcon12.setOnClickListener {

            if (isChatOpen) {
                if (phoneNumber.isNotEmpty()) {
                    val dialIntent = Intent(Intent.ACTION_DIAL)
                    dialIntent.data = Uri.parse("tel:$phoneNumber")
                    startActivity(dialIntent)
                }
            } else {
                if (isAdded) {
                    mViewDataBinding.root.snackbar("You are not connected with rider")
                }
            }

        }

        mViewDataBinding.hatlyIcon1.setOnClickListener {

            if (isChatOpen) {
                showBottomSheetDialog(view)
                inpChat.setText("")

                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                MessageSocketClass.connect2(
                    "${NetworkCallPointsNest.TOKENER}",
                    orderId, this, this
                )


            } else {
                if (isAdded) {
                    mViewDataBinding.root.snackbar("You are not connected with rider")
                }
            }
        }

        if (!mViewModel.uploadReviewImgResponse.hasActiveObservers()) {
            mViewModel.uploadReviewImgResponse.observe(requireActivity()) {
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
                            if (data.isNotEmpty()) {

                                data.forEach {
                                    MessageSocketClass.sendMessageTo(it, this)
                                }
                                MessageSocketClass.sendMessageTo(textMessage, this)
                                imageFiles.clear()
                                dialogUplodeImageAdapter.notifyDataSetChanged()
                                uploaderImgLayout.visibility = View.GONE
                            }


                            Log.d("uploadImagesRes", "onViewCreated: $data")
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        Log.d("uploadImagesRes", "onViewCreated: ${it}")
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
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

        Log.d("TrackorderId", "orderId: }${orderId}")

        val Token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGlmaWNhdGlvbiI6eyJpdiI6IjZiNjQ3NTMzNjkzODM3NjM2ODMyNmIzOTM1MzczODY0IiwiZW5jcnlwdGVkRGF0YSI6IjI1ODJjMTZlZDNhMmQ5OGMwY2YxZjM0Y2U2YWU2MTU3OTJhMjFmMTZmZmM1NjFjOGJmM2ZmYzc3MzYxMGFjYjEifSwidW5pcXVlSWQiOiIzYzBlOTA5OGJhZDM1YmM5ZmMwYmM3NWNhOWYzNTgiLCJpYXQiOjE3MDAwMzk1MDgsImV4cCI6MTAzNDAwMzk1MDh9.FCl1tlmKN6BGdptGDshocH--PJ-TyfuHhBWLa1ig_oM"

        TrackSocketClass.connect2("${NetworkCallPointsNest.TOKENER}", orderId, this)


    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(p0: GoogleMap) {
        if (LocationPermission.requestPermission(requireActivity())) {
            googleMap = p0


//            createPollyLine(origin, LatLng(25.1972295,55.27974699999999))
//            createPollyLine(origin, LatLng(24.938129106790235,66.99413708922103))

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
//            createPollyLine()

        } else {
            if (isAdded) {
                mViewDataBinding.root.snackbar("Allow location")
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createPollyLine(origin: LatLng, destination: LatLng) {
//
//        val destination = LatLng(24.897369355794208, 67.07753405615058)
//        val origin = LatLng(24.90125984648241, 67.1152140082674)

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

        if (isAdded) {
            val polylineOptions = PolylineOptions()
                .addAll(polyline)
                .color(requireActivity().getColor(R.color.colorRed))
                .width(10f) // Line width

            googleMap.clear()
            googleMap.addPolyline(polylineOptions)
            animateCameraAlongPolyline(polyline)
        }


    }


    private fun animateCameraAlongPolyline(polyline: List<LatLng>) {

        googleMap.setOnMapLoadedCallback {
            val startPosition = polyline.first()


            val endPosition = polyline.last()

//        val centerPosition = LatLng(
//            (startPosition.latitude + endPosition.latitude) / 2,
//            (startPosition.longitude + endPosition.longitude) / 2
//        )

//            // Calculate appropriate zoom level to fit the entire polyline
//            val bounds = LatLngBounds.builder()
//                .include(startPosition)
//                .include(endPosition)
//                .build()
//
//            val padding = 100 // Padding in pixels

            val builder = LatLngBounds.builder()
            for (point in polyline) {
                builder.include(point)
            }
            val bounds = builder.build()


// Step 2: Adjust Camera Position
            val padding = 100 // Adjust this value as needed

            googleMap.addMarker(MarkerOptions().position(startPosition).title("Start Marker"))
            val marker = googleMap.addMarker(
                MarkerOptions().position(endPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery_man))
            )


//            googleMap.addMarker(MarkerOptions().position(endPosition).title("End Marker"))

            if (marker != null) {
                animateMarker(marker, endPosition, false)
            }

            // Animate the camera to fit the bounds and center the polyline
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            googleMap.moveCamera(cameraUpdate)

            // Animate the camera to the center position with an appropriate zoom level
//        googleMap.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(centerPosition, 10f)
//        )
        }


    }

    private fun animateMarker(marker: Marker, toPosition: LatLng, hideMarker: Boolean) {
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val proj: Projection = googleMap.projection
        val startPoint = proj.toScreenLocation(marker.position)
        val startLatLng = proj.fromScreenLocation(startPoint)
        val duration: Long = 500
        val interpolator: LinearInterpolator = LinearInterpolator()
        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - start
                val t: Float = interpolator.getInterpolation(elapsed.toFloat() / duration)
                val lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude
                val lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude
                marker.position = LatLng(lat, lng)
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16)
                } else {
                    marker.isVisible = !hideMarker
                }
            }
        })
    }

    private lateinit var chatArrayList: ArrayList<Doc>
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var recChat: RecyclerView
    private lateinit var recDialogBox: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var uploaderImgLayout: ConstraintLayout
    private lateinit var inpChat: EditText

    private lateinit var dialogUplodeImageAdapter: DialogUplodeImageAdapter

    var textMessage = ""

    private fun showBottomSheetDialog(view: View) {

        recChat = view.findViewById(R.id.recChat)
        val imgBackSheet = view.findViewById<ImageView>(R.id.imgBackSheet)
        val imgSend = view.findViewById<ImageView>(R.id.imgSend)
        inpChat = view.findViewById(R.id.inpChat)
        recDialogBox = view.findViewById(R.id.recDialogBox)
        val imgUploader = view.findViewById<ImageView>(R.id.imgUploader)
        val imgUploadCancel = view.findViewById<ImageView>(R.id.imgUploadCancel)
        uploaderImgLayout = view.findViewById(R.id.uploaderImgLayout)

        linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recChat.layoutManager = linearLayoutManager

        chatArrayList = ArrayList()
        imageFiles = ArrayList()

        chatAdapter = ChatAdapter(chatArrayList)
        recChat.adapter = chatAdapter

        imgSend.setOnClickListener {
            textMessage = inpChat.text.toString().trim()

            if (imageFiles.isNotEmpty()) {
                mViewModel.uploadReviewImg(uploadWithRetrofit(imageFiles))
            } else {
                MessageSocketClass.sendMessageTo(textMessage, this)
            }

//            if (textMessage.isNotEmpty()) {
//                MessageSocketClass.sendMessageTo(textMessage, this)
//            } else if (textMessage.isNotBlank()) {
//                MessageSocketClass.sendMessageTo(textMessage, this)
//            }
        }

        imgBackSheet?.setOnClickListener {
            Log.d("sdsdsdsdsd", "onStateChanged: click")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        imgUploader.setOnClickListener {
            pickImagesLauncher.launch("image/*")
        }

        imgUploadCancel.setOnClickListener {
            imageFiles.clear()
            dialogUplodeImageAdapter.notifyDataSetChanged()
            uploaderImgLayout.visibility = View.GONE
        }


        val categoryLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        dialogUplodeImageAdapter = DialogUplodeImageAdapter(imageFiles, this)
        recDialogBox.layoutManager = categoryLayoutManager
        recDialogBox.adapter = dialogUplodeImageAdapter

    }

    override fun onGetAllMessage(getAllChatsData: GetAllMessageData) {
        GlobalScope.launch(Dispatchers.Main) {
//            delay(2000)
            Log.d("onGetAllMessage", "onStateChanged: click $getAllChatsData")
            chatArrayList.clear()
            getAllChatsData.docs.forEach {
                it.isUser = it.from == userId
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


            getAllChatsData.isUser = getAllChatsData.from == userId
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

    override fun getShopData(trackShopModel: TrackShopModel) {
        Log.d("onTrackFragment", "getShopData ${trackShopModel}")

        if (googleMap != null) {
            CoroutineScope(Dispatchers.Main).launch {
                createPollyLine(
                    origin,
                    LatLng(trackShopModel.setting.location.lat, trackShopModel.setting.location.lng)
                )
            }
        }

    }

    override fun getRiderData(trackRiderModel: TrackRiderModel) {

        CoroutineScope(Dispatchers.Main).launch {
            isChatOpen = true
            mViewDataBinding.textView2224.text = trackRiderModel.name
            phoneNumber = trackRiderModel.contact
            Picasso.get().load(trackRiderModel.profileImage).placeholder(R.drawable.hatly_splash_logo_space).error(R.drawable.hatly_splash_logo_space).resize(500, 500)
                .into(mViewDataBinding.hatlyIcon)
        }
    }

    override fun getRemainingdata(remainingdata: String) {
        val jsonObject = JSONObject(remainingdata)
        val deliveryTime = jsonObject.getString("deliveryTime")

        Log.d("onTrackFragment", "deliveryTime ${deliveryTime}")

        CoroutineScope(Dispatchers.Main).launch {
            mViewDataBinding.textView2223.text = "$deliveryTime minutes"
        }
    }

    override fun getCurrentStatus(currentStatus: String) {
        val jsonObject = JSONObject(currentStatus)
        val currentStatus = jsonObject.getString("status")

        CoroutineScope(Dispatchers.Main).launch {
            when (currentStatus) {
                "ready" -> {
                    Log.d("onTrackFragment", "currentStatus ready")
                    mViewDataBinding.imgPlaced.isChecked = true
                    mViewDataBinding.line1.isChecked = true
                    mViewDataBinding.imgPrepared.isChecked = true
                    mViewDataBinding.line2.isChecked = false
                    mViewDataBinding.imgPicked.isChecked = false
                    mViewDataBinding.line3.isChecked = false
                    mViewDataBinding.imgDelivered.isChecked = false
                }

                "placed" -> {
                    mViewDataBinding.imgPlaced.isChecked = true
                    mViewDataBinding.line1.isChecked = false
                    mViewDataBinding.imgPrepared.isChecked = false
                    mViewDataBinding.line2.isChecked = false
                    mViewDataBinding.imgPicked.isChecked = false
                    mViewDataBinding.line3.isChecked = false
                    mViewDataBinding.imgDelivered.isChecked = false
                    Log.d("onTrackFragment", "currentStatus placed")
                }

                "picked" -> {
                    Log.d("onTrackFragment", "currentStatus picked")
                    mViewDataBinding.imgPlaced.isChecked = true
                    mViewDataBinding.line1.isChecked = true
                    mViewDataBinding.imgPrepared.isChecked = true
                    mViewDataBinding.line2.isChecked = true
                    mViewDataBinding.imgPicked.isChecked = true
                    mViewDataBinding.line3.isChecked = false
                    mViewDataBinding.imgDelivered.isChecked = false
                    isChatOpen = true
                }

                "delivered" -> {
                    Log.d("onTrackFragment", "currentStatus delivered")
                    sharedViewModel.orderHistory.value!!.status = "delivered"
                    mViewDataBinding.imgPlaced.isChecked = true
                    mViewDataBinding.line1.isChecked = true
                    mViewDataBinding.imgPrepared.isChecked = true
                    mViewDataBinding.line2.isChecked = true
                    mViewDataBinding.imgPicked.isChecked = true
                    mViewDataBinding.line3.isChecked = true
                    mViewDataBinding.imgDelivered.isChecked = true
                }

                "cancelled" -> {
                    Log.d("onTrackFragment", "currentStatus cancelled")
                    sharedViewModel.orderHistory.value!!.status = "cancelled"
                    mViewDataBinding.imgPlaced.isChecked = false
                    mViewDataBinding.line1.isChecked = false
                    mViewDataBinding.imgPrepared.isChecked = false
                    mViewDataBinding.line2.isChecked = false
                    mViewDataBinding.imgPicked.isChecked = false
                    mViewDataBinding.line3.isChecked = false
                    mViewDataBinding.imgDelivered.isChecked = false
                }

                "confirmed" -> {
                    mViewDataBinding.imgPlaced.isChecked = true
                    mViewDataBinding.line1.isChecked = false
                    mViewDataBinding.imgPrepared.isChecked = false
                    mViewDataBinding.line2.isChecked = false
                    mViewDataBinding.imgPicked.isChecked = false
                    mViewDataBinding.line3.isChecked = false
                    mViewDataBinding.imgDelivered.isChecked = false
                    Log.d("onTrackFragment", "currentStatus confirmed")
                }
            }
        }
    }


    override fun getUpdatedLatLng(latLng: String) {
        val jsonObject = JSONObject(latLng)
        val lat = jsonObject.getString("lat").toDouble()
        val lng = jsonObject.getString("lng").toDouble()

        if (googleMap != null) {
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("onTrackFragment", "latLng ${latLng}")
                val destination = LatLng(lat, lng)
//            origin = LatLng(24.938129106790235, 66.9942872929244)
                createPollyLine(origin, destination)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        MessageSocketClass.disconnect()
        TrackSocketClass.disconnect()
    }


    private lateinit var imageFiles: ArrayList<File>

    private val pickImagesLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
            // Handle the result here


            if (uris != null) {
                imageFiles.clear()

                uris.forEachIndexed { index, uri ->

                    val str = "${requireContext().filesDir}/$index.jpg"

                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        uri
                    )


                    val outputStream = FileOutputStream(str)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                    outputStream.close()

                    if (File(str).exists()) {
                        Log.d("uploadImageArrayList", "exist: $str")
                    }

                    imageFiles.add(File(str))

                }

                dialogUplodeImageAdapter.notifyDataSetChanged()
            }

            if (imageFiles.isNotEmpty()) {
                uploaderImgLayout.visibility = View.VISIBLE
            } else {
                uploaderImgLayout.visibility = View.GONE
            }
        }


    private fun uploadWithRetrofit(imageFiles: List<File>): List<MultipartBody.Part> {


        val imagesList = mutableListOf<MultipartBody.Part>()

        for (imageUri in imageFiles) {
            imagesList.add(prepareFilePart("images", imageUri))
        }


        return imagesList

//        mViewModel.uploadReviewImg(imagesList)

    }

    private fun prepareFilePart(partName: String, fileUri: File): MultipartBody.Part {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), fileUri)
        return MultipartBody.Part.createFormData(partName, fileUri.name, requestFile)
    }

    override fun clickshopItem(position: Int) {

    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }

}