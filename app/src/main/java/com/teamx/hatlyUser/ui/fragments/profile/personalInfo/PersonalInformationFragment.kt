package com.teamx.hatlyUser.ui.fragments.profile.personalInfo

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentPersonalInformationBinding
import com.teamx.hatlyUser.ui.fragments.profile.personalInfo.otpVerification.OtpBottomSheetProfileFragment
import com.teamx.hatlyUser.ui.fragments.profile.personalInfo.otpVerification.ProfileOtpInterface
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class PersonalInformationFragment :
    BaseFragment<FragmentPersonalInformationBinding, PersonalInformationViewModel>(),
    ProfileOtpInterface {

    override val layoutId: Int
        get() = R.layout.fragment_personal_information
    override val viewModel: Class<PersonalInformationViewModel>
        get() = PersonalInformationViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var imageUrl = ""
    private var userName = ""
    private var contact = ""


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
            mViewDataBinding.editText.setText(it.name)
            mViewDataBinding.editText3.text = it.email
            Log.d("PersonalI", "onViewCreated: ${it.contact}")
            if (it.contact == null) {
                mViewDataBinding.editText2.isEnabled = true
            } else {
                mViewDataBinding.editText2.isEnabled = false
                mViewDataBinding.editText2.setText(it.contact)
            }

            Picasso.get().load(it.profileImage).placeholder(R.drawable.hatly_splash_logo_space)
                .error(R.drawable.hatly_splash_logo_space).resize(500, 500)
                .into(mViewDataBinding.hatlyIcon)
            imageUrl = it.profileImage ?: ""
            userName = it.name
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.textView35.setOnClickListener {
            findNavController().navigate(R.id.action_personalInfoFragment_to_changePasswordFragment)
        }

        mViewDataBinding.txtLogin.setOnClickListener {

            if (sharedViewModel.userData.value?.contact == null) {
                contact = mViewDataBinding.editText2.text.toString().trim()
                if (contact.isNotEmpty()) {
                    val params = JsonObject()
                    try {
                        params.addProperty("contact", contact)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    mViewModel.sendOtpProfile(params)
                } else {
                    updateProfile()
                }
            } else {
                updateProfile()
            }
        }

        mViewDataBinding.imgGallery.setOnClickListener {
            fetchImageFromGallery()
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
                                Log.d("uploadReviewIm", "onViewCreated: ${data}")
                                if (data.isNotEmpty()) {
                                    imageUrl = data[0]
                                    Picasso.get().load(imageUrl)
                                        .placeholder(R.drawable.hatly_splash_logo_space)
                                        .error(R.drawable.hatly_splash_logo_space).resize(500, 500)
                                        .into(mViewDataBinding.hatlyIcon)
                                }
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        Log.d("uploadReviewIm", "onViewCreated: ${it.message}")
                        if (isAdded) {

                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }

        if (!mViewModel.updateProfileResponse.hasActiveObservers()) {
            mViewModel.updateProfileResponse.observe(requireActivity()) {
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
                            Picasso.get().load(data.profileImage)
                                .placeholder(R.drawable.hatly_splash_logo_space)
                                .error(R.drawable.hatly_splash_logo_space).resize(500, 500)
                                .into(mViewDataBinding.hatlyIcon)
                            val userData = PrefHelper.getInstance(requireActivity()).getUserData()!!
                            userData.name = data.name
                            Log.d("uploadReviewIm", "profileImage: ${data}")
                            userData.profileImage = data.profileImage
                            PrefHelper.getInstance(requireActivity()).setUserData(userData)
                            sharedViewModel.setUserData(userData)
                            if (isAdded) {
                                mViewDataBinding.root.snackbar("Profile updated")
                            }
                            findNavController().popBackStack()
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }

        if (!mViewModel.sendOtpProfileResponse.hasActiveObservers()) {
            mViewModel.sendOtpProfileResponse.observe(requireActivity()) {
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
                            val otpBottomSheetProfileFragment = OtpBottomSheetProfileFragment(this)
                            if (!otpBottomSheetProfileFragment.isAdded && contact.isNotEmpty()) {
                                otpBottomSheetProfileFragment.show(
                                    parentFragmentManager,
                                    otpBottomSheetProfileFragment.tag
                                )
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }

        if (!mViewModel.verifyOtpProfileResponse.hasActiveObservers()) {
            mViewModel.verifyOtpProfileResponse.observe(requireActivity()) {
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
                            if (isAdded) {
                                mViewDataBinding.root.snackbar("Number updated")
                            }
                            val userData = PrefHelper.getInstance(requireActivity()).getUserData()
                            userData!!.contact = contact
                            PrefHelper.getInstance(requireActivity()).setUserData(userData)
                            sharedViewModel.setUserData(userData)
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }

    }

    private fun fetchImageFromGallery() {
        startForResult.launch("image/*")
    }

    fun updateProfile() {
        userName = mViewDataBinding.editText.text.toString()
        if (userName.isNotEmpty()) {
            val params = JsonObject()
            try {
                params.addProperty("name", userName)
                if (imageUrl.isNotEmpty()) {
                    params.addProperty("profileImage", imageUrl)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            mViewModel.updateProfile(params)
        } else {
            mViewDataBinding.root.snackbar("Enter Username")
        }
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val str = "${requireContext().filesDir}/file.jpg"

                Log.d("startForResult", "Profile image: $it")


//                uploadWithRetrofit(it)

                val imageUri = uri

                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    imageUri
                )

// Compress the bitmap to a JPEG format with 80% quality and save it to a file
                val outputStream = FileOutputStream(str)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                outputStream.close()

//                Picasso.get().load(it).into(mViewDataBinding.hatlyIcon)

                uploadWithRetrofit(File(str))
            }
        }

    private fun uploadWithRetrofit(file: File) {

        val imagesList = mutableListOf<MultipartBody.Part>()

        imagesList.add(prepareFilePart("images", file))

        mViewModel.uploadReviewImg(imagesList)

    }

    private fun prepareFilePart(partName: String, fileUri: File): MultipartBody.Part {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), fileUri)
        return MultipartBody.Part.createFormData(partName, fileUri.name, requestFile)
    }

    override fun setPinView(pinView: String) {

        val params = JsonObject()
        try {
            params.addProperty("contact", contact)
            params.addProperty("verificationCode", pinView)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewModel.verifyOtpProfile(params)
    }


}