package com.app.movieapp.utility

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.app.movieapp.BuildConfig
import com.app.movieapp.baseclass.BaseActivity


/**
 * Created by Rahul Sadhu
 */

object Utils {

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun log(string: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.w("####", string)
        }
    }

    fun startNewActivity(context: Context, intent: Intent) {
        try {
            (context as BaseActivity).startNewActivity(intent)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

