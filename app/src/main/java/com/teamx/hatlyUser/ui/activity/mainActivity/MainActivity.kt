package com.teamx.hatlyUser.ui.activity.mainActivity


import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.SharedViewModel
import com.teamx.hatlyUser.baseclasses.BaseActivity
import com.teamx.hatlyUser.data.local.datastore.DataStoreProvider
import com.teamx.hatlyUser.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


    override val viewModel: Class<MainViewModel>
        get() = MainViewModel::class.java

    override val layoutId: Int
        get() = R.layout.activity_main

    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var dataStoreProvider: DataStoreProvider
    lateinit var sharedViewModel: SharedViewModel

    private var navController: NavController? = null


    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        dataStoreProvider = DataStoreProvider(this)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)





    }



    open fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }


    open fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }



}