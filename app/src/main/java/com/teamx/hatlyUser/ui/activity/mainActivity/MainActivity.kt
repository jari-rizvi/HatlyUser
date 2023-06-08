package com.teamx.hatlyUser.ui.activity.mainActivity


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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

    private lateinit var dataStoreProvider: DataStoreProvider
    lateinit var sharedViewModel: SharedViewModel

    private var navController: NavController? = null


    private lateinit var progressBar: ProgressBar

//    var bottomNav: BottomNavigationView? = null
//    private var floatingActionButton: FloatingActionButton? = null
//    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        dataStoreProvider = DataStoreProvider(this)


        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        mViewDataBinding.drawerLayoutMain.myProfile.setOnClickListener {
            navController!!.navigate(R.id.profileManagementFragment)
        }

        setupBottomNavMenu(navController!!)

        mViewDataBinding.fab.setOnClickListener {
            mViewDataBinding.bottomNav.selectedItemId = R.id.homeBottom
        }

        mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        navController!!.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.homeFragment -> {
                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
                    mViewDataBinding.fab.visibility = View.VISIBLE

                    mViewDataBinding.bottomNav.menu?.getItem(2)?.isChecked = true

                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
                else -> {
                    mViewDataBinding.bottomNav.visibility = View.GONE
                    mViewDataBinding.fab.visibility = View.GONE
                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }

    }

    private fun setupBottomNavMenu(navController: NavController) {
        mViewDataBinding.bottomNav.setupWithNavController(navController)

        mViewDataBinding.bottomNav.setOnItemSelectedListener { menuItem ->
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
                else -> {
                    false
                }
            }
        }

    }


    open fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }


    open fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun openDrawer() {
        if (mViewDataBinding.drawerLayout.isOpen) {
            mViewDataBinding.drawerLayout.openDrawer(GravityCompat.END)
        } else {
            mViewDataBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

}