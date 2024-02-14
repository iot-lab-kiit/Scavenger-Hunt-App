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

#Coroutines---------------------------------------------------------
-keepattributes Signature
-keep class kotlin.coroutines.Continuation
#Coroutines---------------------------------------------------------

#Retrofit-----------------------------------------------------------
-if interface * { @retrofit2.http.* *** *(...); }
-keep,allowobfuscation interface <3>

-keep,allowobfuscation,allowshrinking interface retrofit2.Call
#Retrofit-----------------------------------------------------------

#Gson---------------------------------------------------------------
-keepclassmembers,allowobfuscation class * {
 @com.google.gson.annotations.SerializedName <fields>;
}

-keepattributes Signature
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
#Gson---------------------------------------------------------------

# Keep application classes
-keep class in.iot.lab.** { *; }

# Keep classes that are referenced in your XML layouts
-keepclassmembers class * {
    @androidx.annotation.LayoutRes <init>(...);
}

# Keep ViewModel classes
-keep class * extends androidx.lifecycle.ViewModel {
    <init>();
}

# Keep Hilt-related classes
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keepclassmembers class * {
    @dagger.Provides *;
    @dagger.Binds *;
}

# Keep Retrofit and OkHttp interfaces and their methods
-keep interface retrofit2.** {
    *;
}
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep GSON and Moshi model classes
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep Firebase and Play Services classes
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Keep annotation classes (for Kotlin reflection)
-keepclasseswithmembers public class * {
    kotlin.Metadata *;
}

# Keep classes used by Kotlin reflection
-dontwarn kotlin.reflect.**
-dontwarn kotlin.Metadata