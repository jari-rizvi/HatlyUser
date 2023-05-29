package com.teamx.hatlyUser.ui.activity.mainActivity


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    var bottomNav: BottomNavigationView? = null
    private var floatingActionButton: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        dataStoreProvider = DataStoreProvider(this)

        bottomNav = findViewById(R.id.bottom_nav_instructor)

        floatingActionButton = findViewById(R.id.fab)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavMenu(navController!!)

        floatingActionButton?.setOnClickListener {
            bottomNav?.selectedItemId = R.id.homeBottom
        }

        navController!!.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("navController", "onCreate: ${destination.id}")
            when (destination.id) {

                R.id.homeFragment -> {
                    bottomNav?.visibility = View.VISIBLE
                    floatingActionButton?.visibility = View.VISIBLE
                }
                else -> {
                    bottomNav?.visibility = View.GONE
                    floatingActionButton?.visibility = View.GONE
                }
            }
        }

        bottomNav?.selectedItemId = R.id.homeBottom
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottomNav?.setupWithNavController(navController)

        bottomNav?.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuBottom -> {
                    // Handle home action
                    Log.d("navController", "onCreate: menuBottom")
                    true
                }
                R.id.wishlistBottom -> {
                    // Handle search action
                    Log.d("navController", "onCreate: wishlistBottom")
                    true
                }
                R.id.homeBottom -> {
                    // Handle notifications action
                    Log.d("navController", "onCreate: homeBottom")
                    navController.navigate(R.id.homeFragment, null)
                    true
                }
                R.id.cartBottom -> {
                    // Handle profile action
                    Log.d("navController", "onCreate: cartBottom")
                    true
                }

                R.id.profileBottom -> {
                    // Handle profile action
                    Log.d("navController", "onCreate: profileBottom")
                    true
                }
                else -> false
            }
        }

    }


    open fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }


    open fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }


}