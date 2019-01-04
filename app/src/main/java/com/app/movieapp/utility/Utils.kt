package com.app.movieapp.utility

import android.content.Context
import android.widget.Toast
import com.app.movieapp.BuildConfig


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
}

