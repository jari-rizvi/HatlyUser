package com.teamx.hatlyUser.ui.fragments.auth.login

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.TOKENER
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentLoginBinding
import com.teamx.hatlyUser.utils.LocationPermission
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import kotlin.random.Random

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_login
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var userEmail: String? = null
    private var userPass: String? = null
    private var randNum: String? = null

    private lateinit var googleSignInClient: GoogleSignInClient
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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        PrefHelper.getInstance(requireActivity()).setNotFirstTime(true)

        randNum = generateRandom().toString()

        mViewDataBinding.textView4.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        mViewDataBinding.textView5.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            if (isValidate()) {
                initialization()
                mViewModel.login(createParams())
            }
        }

        mViewDataBinding.txtLoginGoogle.setOnClickListener {
            signInGoogle()
        }

        if (!mViewModel.loginResponse.hasActiveObservers()) {
            mViewModel.loginResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            CoroutineScope(Dispatchers.Main).launch {
                                data.token.let { it1 ->
                                    Log.d("loginWithGoogleResponse", "onViewCreated: $it1")
                                    dataStoreProvider.saveUserToken(it1)
                                    TOKENER = it1
                                }

//                                    sharedViewModel.setUserData(data)

//                                    (requireActivity() as ProfileInterFace).profileData(data)
//                                    dataStoreProvider.saveDeviceData(randNum!!)
//                                    dataStoreProvider.saveDeviceData("88765275963748185512")
                            }
                            PrefHelper.getInstance(requireActivity()).setUserData(data)
                            if (LocationPermission.requestPermission(requireContext())) {
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            } else {
                                findNavController().navigate(R.id.action_loginFragment_to_locationFragment)
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
            })
        }

        if (!mViewModel.loginWithGoogleResponse.hasActiveObservers()) {
            mViewModel.loginWithGoogleResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            CoroutineScope(Dispatchers.Main).launch {

                                data.token.let { it1 ->
                                    Log.d("loginWithGoogleResponse", "onViewCreated: $it1")
                                    dataStoreProvider.saveUserToken(it1)
                                    TOKENER = it1
                                }

//                                    sharedViewModel.setUserData(data)
//                                    (requireActivity() as ProfileInterFace).profileData(data)

//                                    dataStoreProvider.saveDeviceData(randNum!!)
//                                    dataStoreProvider.saveDeviceData("88765275963748185512")
                            }
                            PrefHelper.getInstance(requireActivity()).setUserData(data)
                            if (LocationPermission.requestPermission(requireContext())) {
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            } else {
                                findNavController().navigate(R.id.action_loginFragment_to_locationFragment)
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
            })
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

    private fun initialization() {
        userEmail = mViewDataBinding.userEmail.text.toString().trim()
        userPass = mViewDataBinding.userPassword.text.toString().trim()
    }

    private fun createParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", userEmail.toString())
            params.addProperty("password", userPass.toString())
//            params.addProperty("deviceData", randNum)
//            params.addProperty("deviceData", "88765275963748185512")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return params
    }

    private fun isValidate(): Boolean {

        if (mViewDataBinding.userEmail.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter email or phone")
            return false
        }
        if (mViewDataBinding.userPassword.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter password")
            return false
        }
        return true
    }

    private fun generateRandom(): Long {
        val rangeStart = 1000000000000000000L
        val rangeEnd = 9000000000000000000L

        return Random.nextLong(rangeStart, rangeEnd)
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                handleResults(task)
            } else {
                Log.d("onSignInResult", "else: ${result.resultCode}")
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {

        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        Log.d("onSignInResult", "updateUI: ${account.idToken}")
        val params = JsonObject()
        try {
            if (!account.idToken.isNullOrEmpty()) {
                params.addProperty("token", account.idToken)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        if (params.has("token")) {
            mViewModel.loginWithGoogle(params)
        } else {
            mViewDataBinding.root.snackbar("Login Failed")
        }
    }

}