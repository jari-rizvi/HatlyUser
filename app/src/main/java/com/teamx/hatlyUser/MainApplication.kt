import com.google.firebase.BuildConfig
import com.teamx.hatlyUser.localization.LocaleManager
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        var application: Application? = null
            private set
        val context: Context
            get() = application!!.applicationContext
        val PACKAGE_NAME: String
            get() = application!!.packageName

        var localeManager: LocaleManager? = null

    }

    override fun onCreate() {
        super.onCreate()

        application = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }



        FirebaseApp.initializeApp(applicationContext)

        /*      val YOUR_CLIENT_ID: String =
                  "AdvlcnQvcP-9V2-2q6eW5htoG0Z3GjLuAVnY0e1nxD-AjVte_BBNlvJvFSZ59hnqfnIDPJEYSq4p34aE"
              //myId
      //            "AYhqwOVlI7qD3d_blV2yF6no1XCYgpz8lPTuGNLkUaopBc5z5nlNelmKDozOaRbL6esX_9ASXslEL9e0"
              val config = CheckoutConfig(
                  application = this,
                  clientId = YOUR_CLIENT_ID,
                  environment = Environment.SANDBOX,
                  returnUrl = "nativexo://paypalpay",
                  currencyCode = CurrencyCode.USD,
                  userAction = UserAction.PAY_NOW,
                  settingsConfig = SettingsConfig(
                      loggingEnabled = true
                  )
              )

      //config.ur
              PayPalCheckout.setConfig(config)*/


        //
//        createNotificationChannel()
    }

    override fun attachBaseContext(base: Context?) {
        localeManager = LocaleManager(base!!)
        super.attachBaseContext(localeManager!!.setLocale(base!!))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig!!)
        localeManager!!.setLocale(this)
    }


    //notifications
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                CounterNotificationService.COUNTER_CHANNEL_ID,
//                "Counter",
//                NotificationManager.IMPORTANCE_HIGH
//            )
//            channel.description = "Used for the increment counter notifications"
//
//            val notificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }


}