package com.teamx.hatlyUser.baseclasses

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.SharedViewModel
import com.teamx.hatlyUser.data.local.datastore.DataStoreProvider
import com.teamx.hatlyUser.ui.activity.mainActivity.MainActivity
import com.teamx.hatlyUser.utils.DialogHelperClass


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    lateinit var sharedViewModel: SharedViewModel
    lateinit var navController: NavController
    lateinit var options: NavOptions
    lateinit var dataStoreProvider: DataStoreProvider

    private var mActivity: BaseActivity<*, *>? = null
    lateinit var mViewDataBinding: T
    protected lateinit var mViewModel: V

    abstract val layoutId: Int
    abstract val viewModel: Class<V>
    abstract val bindingVariable: Int

    protected lateinit var loadingDialog: Dialog
    var lang = "en"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mViewDataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(bindingVariable, mViewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()


        subscribeToShareLiveData()
        subscribeToNavigationLiveData()
        subscribeToNetworkLiveData()
        subscribeToViewLiveData()


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>)
            this.mActivity = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = DialogHelperClass.loadingDialog(requireContext())
        mViewModel = ViewModelProvider(this)[viewModel]


        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        dataStoreProvider = DataStoreProvider(requireContext())
        navController = NavController(requireContext())

    }

    open fun subscribeToViewLiveData() {

        //observe view data

    }

    open fun setNewLocale(language: String?, restartProcess: Boolean): Boolean {
        if (language != null) {
            MainApplication.localeManager!!.setNewLocale(requireActivity(), language)
        }
        val i = Intent(requireActivity(), MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
        if (restartProcess) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        return true
    }


    open fun subscribeToShareLiveData() {

    }

    open fun getMainActivity(): MainActivity? {
        return activity as MainActivity?
    }

    open fun subscribeToNetworkLiveData() {
        //All Network Tasks
    }

    open fun subscribeToNavigationLiveData() {

    }

    open fun showProgressBar() {
        (activity as MainActivity).showProgressBar()
    }

    open fun hideProgressBar() {
        (activity as MainActivity).hideProgressBar()
    }

    fun navigateFragmentMethod(fragment: Int, isNavigate: Boolean) {

        if (isNavigate) {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(fragment, null)
        }

    }

    open fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    open fun popUpStack() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.popBackStack()

    }


}