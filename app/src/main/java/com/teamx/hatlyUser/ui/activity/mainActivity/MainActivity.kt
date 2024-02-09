package com.teamx.hatlyUser.ui.activity.mainActivity


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.SharedViewModel
import com.teamx.hatlyUser.baseclasses.BaseActivity
import com.teamx.hatlyUser.data.local.datastore.DataStoreProvider
import com.teamx.hatlyUser.databinding.ActivityMainBinding
import com.teamx.hatlyUser.utils.CounterNotificationService
import com.teamx.hatlyUser.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

    private lateinit var googleSignInClient: GoogleSignInClient


    private var modeChangeReceiver: BroadcastReceiver? = null

    override fun onResume() {
        super.onResume()

        // Register the BroadcastReceiver to listen for system mode changes
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED)
        registerReceiver(modeChangeReceiver, filter)
    }

    override fun onPause() {
        super.onPause()

        // Unregister the BroadcastReceiver when the activity is paused
        unregisterReceiver(modeChangeReceiver)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = CounterNotificationService(applicationContext)

        modeChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_CONFIGURATION_CHANGED) {
                    val currentNightMode =
                        resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                    if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                        // Dark mode is enabled
//                        finish()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                        startActivity(getIntent())
//                        Toast.makeText(context, "Dark Mode Enabled", Toast.LENGTH_SHORT).show()
                    } else {
                        // Light mode is enabled
//                        finish()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                        startActivity(getIntent())
//                        Toast.makeText(context, "Light Mode Enabled", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        dataStoreProvider = DataStoreProvider(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        mViewDataBinding.drawerLayoutMain.myProfile.setOnClickListener {
            navController!!.navigate(R.id.profileManagementFragment)
        }

        mViewDataBinding.drawerLayoutMain.language.setOnClickListener {
            navController!!.navigate(R.id.languageFragment)
        }

        mViewDataBinding.drawerLayoutMain.settings.setOnClickListener {
            navController!!.navigate(R.id.settingFragment)
        }

//        mViewDataBinding.drawerLayoutMain.trackOrder.setOnClickListener {
//            navController!!.navigate(R.id.trackFragment)
//        }

        mViewDataBinding.drawerLayoutMain.myWallet.setOnClickListener {
            navController!!.navigate(R.id.walletFragment)
        }

        mViewDataBinding.drawerLayoutMain.wishlist.setOnClickListener {
            navController!!.navigate(R.id.wishListFragment)
        }

        mViewDataBinding.drawerLayoutMain.logout.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener() {
                if (it.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        dataStoreProvider.removeAll()
                        PrefHelper.getInstance(this@MainActivity).clearAll()
                    }
                }

            }
            navController!!.navigate(R.id.action_homeFragment_to_guestFragment)
        }

        mViewDataBinding.drawerLayoutMain.notification.setOnClickListener {
            navController!!.navigate(R.id.notificationFragment)
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

                    mViewDataBinding.bottomNav.menu.getItem(1)?.isChecked = true

                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }

                R.id.storesFragment -> {
                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
                    mViewDataBinding.fab.visibility = View.VISIBLE

                    mViewDataBinding.bottomNav.menu.getItem(1)?.isChecked = true

                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.hatlyMartFragment -> {
                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
                    mViewDataBinding.fab.visibility = View.VISIBLE

                    mViewDataBinding.bottomNav.menu.getItem(1)?.isChecked = true

                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.shopHomeFragment -> {
                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
                    mViewDataBinding.fab.visibility = View.VISIBLE

                    mViewDataBinding.bottomNav.menu.getItem(1)?.isChecked = true

                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.productPreviewFragment -> {
                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
                    mViewDataBinding.fab.visibility = View.VISIBLE

                    mViewDataBinding.bottomNav.menu.getItem(1)?.isChecked = true

                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.foodsHomeFragment -> {
                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
                    mViewDataBinding.fab.visibility = View.VISIBLE

                    mViewDataBinding.bottomNav.menu.getItem(1)?.isChecked = true

                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.foodsShopHomeFragment -> {
                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
                    mViewDataBinding.fab.visibility = View.VISIBLE

                    mViewDataBinding.bottomNav.menu.getItem(1)?.isChecked = true

                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.cartFragment -> {
                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
                    mViewDataBinding.fab.visibility = View.VISIBLE

                    mViewDataBinding.bottomNav.menu.getItem(0)?.isChecked = true
                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

//                R.id.wishListFragment -> {
//                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
//                    mViewDataBinding.fab.visibility = View.VISIBLE
//
//                    mViewDataBinding.bottomNav.menu.getItem(1)?.isChecked = true
//                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//                }

                R.id.profileManagementFragment -> {
                    mViewDataBinding.bottomNav.visibility = View.VISIBLE
                    mViewDataBinding.fab.visibility = View.VISIBLE

                    mViewDataBinding.bottomNav.menu.getItem(2)?.isChecked = true
                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                else -> {
                    mViewDataBinding.bottomNav.visibility = View.GONE
                    mViewDataBinding.fab.visibility = View.GONE
                    mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }

        sharedViewModel.userData.observe(this) {
            mViewDataBinding.drawerLayoutMain.textView14.text = it.name
//            if (it.profileImage == null){
//                Picasso.get().load(R.drawable.food).resize(500, 500)
//                    .into(mViewDataBinding.drawerLayoutMain.imgProfile)
//            }else{
//
//            }
            Picasso.get().load(it.profileImage).placeholder(R.drawable.hatly_splash_logo_space)
                .error(R.drawable.hatly_splash_logo_space).resize(500, 500)
                .into(mViewDataBinding.drawerLayoutMain.imgProfile)

            Log.d("userData", "it._id: ${it._id}")
            Log.d("userData", "it.name: ${it.name}")
            Log.d("userData", "it.contact: ${it.contact}")
            Log.d("userData", "it.email: ${it.email}")
            Log.d("userData", "it.token: ${it.token}")
            Log.d("userData", "it.verified: ${it.verified}")
            Log.d("userData", "it.profileImage: ${it.profileImage}")
        }

        if (intent != null && intent.extras != null) {
            val str = intent.extras?.getString("order_id")
            Log.d("order_Idorder_Id", "mainActivity: $str")
            val bundle = Bundle()
            bundle.putString("order_id", str)
            navController!!.navigate(R.id.orderDetailFragment, bundle)
        }

    }

    private fun setupBottomNavMenu(navController: NavController) {
        mViewDataBinding.bottomNav.setupWithNavController(navController)

        mViewDataBinding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
//                R.id.menuBottom -> {
//                    // Handle home action
//                    Log.d("navController", "onCreate: menuBottom")
//                    true
//                }
//
//                R.id.wishlistBottom -> {
//                    if (!alreadyFragmentAdded(R.id.wishListFragment)) {
//                        navController.navigate(R.id.wishListFragment, null)
//                    }
//                    true
//                }

                R.id.homeBottom -> {
                    if (!alreadyFragmentAdded(R.id.homeFragment)) {
                        navController.navigate(R.id.homeFragment, null)
                    }
                    true
                }

                R.id.cartBottom -> {

                    if (!alreadyFragmentAdded(R.id.cartFragment)) {
                        navController.navigate(R.id.cartFragment, null)
                    }
                    true
                }

                R.id.profileBottom -> {
                    // Handle profile action
                    Log.d("navController", "onCreate: profileBottom")

                    if (!alreadyFragmentAdded(R.id.profileManagementFragment)) {
                        navController.navigate(R.id.profileManagementFragment, null)
                    }

                    true
                }

                else -> {
                    false
                }
            }
        }

    }

    private fun alreadyFragmentAdded(fragment: Int): Boolean {
        val fragmentAlreadyAdded = navController?.currentDestination?.id == fragment
        if (fragmentAlreadyAdded) {
            // Fragment not found, navigate to it
            return true
        }
        return false
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


    companion object {
        var service: CounterNotificationService? = null
    }

}

