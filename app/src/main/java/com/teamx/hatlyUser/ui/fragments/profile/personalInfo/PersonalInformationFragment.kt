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
class PersonalInformationFragment : BaseFragment<FragmentPersonalInformationBinding, PersonalInformationViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_personal_information
    override val viewModel: Class<PersonalInformationViewModel>
        get() = PersonalInformationViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    var imageUrl = ""
    var userName = ""


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

        sharedViewModel.userData.observe(requireActivity()){
            mViewDataBinding.editText.setText(it.name)
            mViewDataBinding.editText3.text = it.email
            mViewDataBinding.editText2.text = it.contact ?: "Not Register"
            Picasso.get().load(it.profileImage).resize(500,500).into(mViewDataBinding.hatlyIcon)
            imageUrl = it.profileImage
            userName = it.name
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.textView35.setOnClickListener {
            findNavController().navigate(R.id.action_personalInfoFragment_to_changePasswordFragment)
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            userName = mViewDataBinding.editText.text.toString()
            if (userName.isNotEmpty()){
                val params = JsonObject()
                try {
                    params.addProperty("name", userName)
                    params.addProperty("profileImage", imageUrl)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                mViewModel.updateProfile(params)
            }else{
                mViewDataBinding.root.snackbar("Enter Username")
            }

        }

        mViewDataBinding.imgGallery.setOnClickListener {
            fetchImageFromGallery()
        }

        if (!mViewModel.uploadReviewImgResponse.hasActiveObservers()) {
            mViewModel.uploadReviewImgResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.isNotEmpty()) {
                                Log.d("uploadReviewIm", "onViewCreated: ${data[0]}")
                                if (data.isNotEmpty()){
                                    imageUrl = data[0]
                                    Picasso.get().load(imageUrl).resize(500,500).into(mViewDataBinding.hatlyIcon)
                                }
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        Log.d("uploadReviewIm", "onViewCreated: ${it.message}")
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }

        if (!mViewModel.updateProfileResponse.hasActiveObservers()) {
            mViewModel.updateProfileResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            Picasso.get().load(data.profileImage).resize(500,500).into(mViewDataBinding.hatlyIcon)
                            val userData = PrefHelper.getInstance(requireActivity()).getUserData()
                            userData!!.name = data.name
                            userData!!.profileImage = data.profileImage
                            PrefHelper.getInstance(requireActivity()).setUserData(userData)
                            sharedViewModel.setUserData(userData)
                            mViewDataBinding.root.snackbar("Profile updated")
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }

    }

    private fun fetchImageFromGallery() {
        startForResult.launch("image/*")
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


}