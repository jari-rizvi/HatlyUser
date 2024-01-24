# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.Dashboard
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.ShopX
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.BalanceX
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.Banner
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.TopRatedProduct
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.Category
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.CategoryX
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.Children
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.DashboardData
#-keep public class com.teamx.hatlyUser.dataclasses.dashboard.DashboardData
#-keep public class com.teamx.hatlyUser.dataclasses.Address
#-keep public class com.teamx.hatlyUser.dataclasses.login.Addres
#-keep public class com.teamx.hatlyUser.dataclasses.login.User
#-keep public class com.teamx.hatlyUser.dataclasses.login.UserX
#-keep public class com.teamx.hatlyUser.dataclasses.login.UserXX
#-keep public class com.teamx.hatlyUser.dataclasses.BillingAddress
#-keep public class com.teamx.hatlyUser.dataclasses.login.LoginData
#-keep public class com.teamx.hatlyUser.dataclasses.ShippingAddress
#-keep public class com.teamx.hatlyUser.dataclasses.UploadModelData
#-keep public class com.teamx.hatlyUser.dataclasses.CheckoutDataMV
#-keep public class com.teamx.hatlyUser.dataclasses.AddressByID
#-keep public class com.teamx.hatlyUser.dataclasses.Settings
#-keep public class com.teamx.hatlyUser.dataclasses.SettingsX
#-keep public class com.teamx.hatlyUser.dataclasses.Shop
#-keep public class com.teamx.hatlyUser.dataclasses.Option
#-keep public class com.teamx.hatlyUser.dataclasses.VariationOption
#-keep public class com.teamx.hatlyUser.dataclasses.ProfileX
#-keep public class com.teamx.hatlyUser.dataclasses.Customer
#-keep public class com.teamx.hatlyUser.dataclasses.Product

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**
-keep class com.google.maps.** { *; }

# Stripe
-keep class com.stripe.android.** { *; }
-keepclassmembers class com.stripe.android.** { *; }
-dontwarn com.stripe.android.**

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.stream.** { *; }

# OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Okio
-dontwarn okio.**
-keep class okio.** { *; }

# Retrofit
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Logging
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-dontwarn org.slf4j.**
-keep class org.slf4j.** { *; }

# Other
-keep class com.google.android.gms.** { *; }
-keep class com.google.firebase.** { *; }


