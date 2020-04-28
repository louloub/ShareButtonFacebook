package com.example.sharebuttonfacebook

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    /*var callbackManager: CallbackManager? = null
    var shareDialog: ShareDialog? = null*/
    var buttonFacebookShare: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonFacebookShare = findViewById(R.id.button_facebook_share)

        isFbAppInstalled()

        buttonFacebookShare?.setOnClickListener{

            if (isFbAppInstalled()) {
                val facebookUrl = "https://www.facebook.com/YourDJToulouse/posts/2779019702166919"
                getOpenFacebookIntent(this.packageManager,facebookUrl)
                val facebookIntent = Intent(Intent.ACTION_VIEW)
                val facebookUrlApp: String = getFacebookURL(this,facebookUrl)
                facebookIntent.data = Uri.parse(facebookUrlApp)
                this.startActivity(facebookIntent)
                /*
                val uri = Uri.parse("fb://facewebmodal/f?href=$url")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()))
                startActivity(intent)*/
            }

            /*var content: ShareLinkContent? = ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://developers.facebook.com"))
                    .build()
            val intent = intent (this,)*/
        }
        // init()
    }

    fun getOpenFacebookIntent(pm: PackageManager, url: String): Intent? {
        var uri = Uri.parse(url)
        try {
            val applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0)
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=$url")
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return Intent(Intent.ACTION_VIEW, uri)
    }

    // method to get the right URL to use in the intent
    fun getFacebookURL(context: Context, url: String): String {
        val packageManager = context.packageManager
        return try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            if (versionCode >= 3002850) { // newer versions of fb app
                // "fb://facewebmodal/f?href=" + url + "posts"
                "fb://facewebmodal/f?href=$url"

            } else { // older versions of fb app
                "fb://page/$url"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            url // normal web url
        }
    }

    /*private fun init() {
        callbackManager = CallbackManager.Factory.create()
        shareDialog = ShareDialog(this)

        val content = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build()

        *//*shareDialog!!.registerCallback(
                callbackManager,
                FacebookCallback<Sharer.Result>)*//*
    }*/

    private fun isFbAppInstalled():Boolean{
        try {
            val info = packageManager.getApplicationInfo("com.facebook.katana", 0)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }

    }
}
