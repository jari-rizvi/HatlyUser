package com.teamx.hatlyUser.ui.fragments.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.MART
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentHomeBinding
import com.teamx.hatlyUser.ui.activity.mainActivity.MainActivity
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: Class<HomeViewModel>
        get() = HomeViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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


        /////////

//        val yourNumber = 123456
//
//        // Arabic locale
//        val locale = Locale("ar")
//
//        // Create a custom NumberFormat for Arabic
//        val arabicNumberFormat = NumberFormat.getInstance(locale)
//        arabicNumberFormat.maximumFractionDigits = 2 // Customize as needed
//
//        // Format the number to Arabic font
//        val arabicNumberString = arabicNumberFormat.format(yourNumber)
//
//        Log.d("arabicNumberString", "arabicNumberString: ${arabicNumberString}")


        ////////








//        MainActivity.service!!.showNotification1("dummy title","dummy description","dummy type","dummy id")

        mViewDataBinding.inpSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeSearchFragment)
        }

        mViewDataBinding.imgWishList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_wishListFragment)
        }

        val userData = PrefHelper.getInstance(requireActivity()).getUserData()

        userData?.let {
            Log.d("setUserData", "onViewCreated: ${it}")

            sharedViewModel.setUserData(it)

            if (it.location?.address == null){
                return@let
            }

            mViewDataBinding.textView20.text = try {
                it.location!!.address
            } catch (e: Exception) {
                "Select your location"
            }

            var labelStr= ""

            labelStr = when (it.location!!.label) {
                "Home" -> {
                    getString(R.string.home)
                }

                "Work" -> {
                    getString(R.string.work)
                }

                else -> {
                    it.location!!.label
                }
            }

            mViewDataBinding.textView41.text = try {
                labelStr
            } catch (e: Exception) {
                "Current Location:"
            }

        }

        mViewDataBinding.homeAddress.setOnClickListener {
            val locationModel = userData?.location
//            locationModel!!.isAction =  "Update"

            if (locationModel != null) {
                if (locationModel._id != null) {
                    locationModel.isAction = "Update"
                }
            }

            sharedViewModel.setlocationmodel(locationModel)
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }

        mViewDataBinding.imgMenu.setOnClickListener {
            val activity = requireActivity() as MainActivity
            activity.openDrawer()
        }

        mViewDataBinding.textView9.setOnClickListener {
            MART = Marts.HATLY_MART
            val bundle = Bundle()
            bundle.putBoolean("parcel", true)
            if (isAdded) {
                findNavController().navigate(R.id.action_homeFragment_to_hatlyMartFragment, bundle)
            }
        }

        mViewDataBinding.textView8.setOnClickListener {
            MART = Marts.FOOD
            findNavController().navigate(R.id.action_homeFragment_to_foodsHomeFragment)
        }

        mViewDataBinding.textView10.setOnClickListener {
            MART = Marts.GROCERY
//            findNavController().navigate(R.id.action_homeFragment_to_storesFragment)
            val bundle = Bundle()
            bundle.putBoolean("parcel", false)
            findNavController().navigate(R.id.action_homeFragment_to_storesFragment, bundle)
        }

        mViewDataBinding.textView11.setOnClickListener {
            MART = Marts.HEALTH_BEAUTY
            val bundle = Bundle()
            bundle.putBoolean("parcel", false)
            findNavController().navigate(R.id.action_homeFragment_to_storesFragment, bundle)
        }

        mViewDataBinding.textView112.setOnClickListener {
            MART = Marts.HOME_BUSINESS
            val bundle = Bundle()
            bundle.putBoolean("parcel", false)
            findNavController().navigate(R.id.action_homeFragment_to_storesFragment, bundle)
        }

        mViewDataBinding.imgNotification.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
        }

        pushNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

        Firebase.initialize(requireContext())
        FirebaseApp.initializeApp(requireContext())
        if (!mViewModel.fcmResponse.hasActiveObservers()) {
            getFcmToken()
        }


//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            askNotificationPermission()
//        }



        if (!mViewModel.fcmResponse.hasActiveObservers()) {
            mViewModel.fcmResponse.observe(requireActivity()) {
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
                            mViewDataBinding.mainLayout.snackbar(data.message)
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {
                            mViewDataBinding.mainLayout.snackbar(it.message!!)
                        }
                    }
                }
            }
        }


        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("handleOnBackPressed", "handleOnBackPressed: back")
                requireActivity().finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )


    }


    private fun getFcmToken() {
        Log.d("fcmToken", "askNotificationPermission")
        // This is only necessary for API level >= 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("123123", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
//            fcmToken = task.result
            val params = JsonObject()
            params.addProperty("fcmToken", task.result)


            mViewModel.fcm(params)
            Log.d("fcmToken", "gaya ${params}")


        })
        // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                Log.d("fcmToken", "POST_NOTIFICATIONS")
//            } else {
//                Log.d("fcmToken", "else")
//            }
//        }
    }


    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)

        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("fcmToken", "PERMISSION_GRANTED")
            if (!mViewModel.fcmResponse.hasActiveObservers()) {
                getFcmToken()
            }

            // FCM SDK (and your app) can post notifications.
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            Log.d("fcmToken", "shouldShowRequestPermissionRationale")
            if (!mViewModel.fcmResponse.hasActiveObservers()) {
                getFcmToken()
            }
        } else {
            // Directly ask for t
            //
            //
            // he permission
            Log.d("fcmToken", "else")
//            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

    }


    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            Log.d("fcmToken", "granted")


        } else {
            Log.d("fcmToken", "granted else")
        }
    }


}