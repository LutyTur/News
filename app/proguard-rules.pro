# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Maciej1.Maciej\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn com.squareup.picasso.**
-dontnote com.squareup.picasso.**
-dontwarn com.hannesdorfmann.mosby.**
-dontnote com.hannesdorfmann.mosby.**
-dontwarn org.codehaus.mojo.**
-dontnote org.codehaus.mojo.**
-dontwarn com.squareup.**
-dontnote com.squareup.**
-dontwarn okio.**
-dontnote okio.**
-dontwarn retrofit2.Platform$Java8