package com.app.movieapp.baseclass

import android.app.Application
import com.app.movieapp.db.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}