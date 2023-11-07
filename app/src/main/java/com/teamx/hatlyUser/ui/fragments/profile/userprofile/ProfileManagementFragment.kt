package com.teamx.hatlyUser.ui.fragments.profile.userprofile

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentLoginBinding
import com.teamx.hatlyUser.databinding.FragmentProfileManagementBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.LoginViewModel
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONException
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class ProfileManagementFragment :
    BaseFragment<FragmentProfileManagementBinding, ProfileManagementViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_profile_management
    override val viewModel: Class<ProfileManagementViewModel>
        get() = ProfileManagementViewModel::class.java
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

        sharedViewModel.userData.observe(requireActivity()) {
            mViewDataBinding.textView.text = it.name
            Picasso.get().load(it.profileImage).resize(500,500).into(mViewDataBinding.hatlyIcon)
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        mViewDataBinding.userPersonal.setOnClickListener {
            findNavController().navigate(R.id.action_profileManagementFragment_to_personalInfoFragment)
        }

        mViewDataBinding.userLocation.setOnClickListener {
            findNavController().navigate(R.id.action_profileManagementFragment_to_locationFragment)
        }

        mViewDataBinding.userOtherHistory.setOnClickListener {
            findNavController().navigate(R.id.action_profileManagementFragment_to_orderHistoryFragment)
        }

        mViewDataBinding.specialOrderHistory.setOnClickListener {
            findNavController().navigate(R.id.action_profileManagementFragment_to_specialOrderHistoryFragment)
        }



//        mViewDataBinding.imgGallery.setOnClickListener {
//            fetchImageFromGallery()
//        }

//        if (!mViewModel.uploadReviewImgResponse.hasActiveObservers()) {
//            mViewModel.uploadReviewImgResponse.observe(requireActivity()) {
//                when (it.status) {
//                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                    }
//
//                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        it.data?.let { data ->
//                            if (data.isNotEmpty()) {
//                                Log.d("uploadReviewIm", "onViewCreated: ${data[0]}")
//                                val params = JsonObject()
//                                try {
//                                    params.addProperty(
//                                        "name",
//                                        sharedViewModel.userData.value!!.name
//                                    )
//                                    params.addProperty("profileImage", data[0])
//                                } catch (e: JSONException) {
//                                    e.printStackTrace()
//                                }
//
//                                mViewModel.updateProfile(params)
//                            }
//                        }
//                    }
//
//                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        Log.d("uploadReviewIm", "onViewCreated: ${it.message}")
//                        mViewDataBinding.root.snackbar(it.message!!)
//                    }
//                }
//            }
//        }

//        if (!mViewModel.updateProfileResponse.hasActiveObservers()) {
//            mViewModel.updateProfileResponse.observe(requireActivity()) {
//                when (it.status) {
//                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                    }
//
//                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        it.data?.let { data ->
//                            Picasso.get().load(data.profileImage).into(mViewDataBinding.hatlyIcon)
//                            val userData = PrefHelper.getInstance(requireActivity()).getUserData()
//                            userData!!.profileImage = data.profileImage
//                            PrefHelper.getInstance(requireActivity()).setUserData(userData)
//                            sharedViewModel.setUserData(userData)
//                        }
//                    }
//
//                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.root.snackbar(it.message!!)
//                    }
//                }
//            }
//        }

    }


//    private fun fetchImageFromGallery() {
//        startForResult.launch("image/*")
//    }
//
//    private val startForResult =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            uri?.let {
//                val str = "${requireContext().filesDir}/file.jpg"
//
//                Log.d("startForResult", "Profile image: $it")
//
//
////                uploadWithRetrofit(it)
//
//                val imageUri = uri
//
//                val bitmap = MediaStore.Images.Media.getBitmap(
//                    requireActivity().contentResolver,
//                    imageUri
//                )
//
//// Compress the bitmap to a JPEG format with 80% quality and save it to a file
//                val outputStream = FileOutputStream(str)
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
//                outputStream.close()
//
////                Picasso.get().load(it).into(mViewDataBinding.hatlyIcon)
//
//                uploadWithRetrofit(File(str))
//            }
//        }
//
//    private fun uploadWithRetrofit(file: File) {
//
//        val imagesList = mutableListOf<MultipartBody.Part>()
//
//        imagesList.add(prepareFilePart("images", file))
//
//        mViewModel.uploadReviewImg(imagesList)
//
//    }
//
//    private fun prepareFilePart(partName: String, fileUri: File): MultipartBody.Part {
//        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), fileUri)
//        return MultipartBody.Part.createFormData(partName, fileUri.name, requestFile)
//    }


}