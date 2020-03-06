package com.example.gitrepos.view.home

import android.app.Application
import com.example.gitrepos.di.appModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

class MyAplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        Stetho.initializeWithDefaults(this)

//        startKoin(this, listOf(appModule))
    }
}