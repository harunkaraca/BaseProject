package com.hk.baseproject.di

import android.app.Application
import com.hk.baseproject.BuildConfig
import com.hk.baseproject.di.AppComponent
import com.hk.baseproject.di.DaggerAppComponent
import timber.log.Timber

class MyApplication: Application() {
    // Reference to the application graph that is used across the whole app
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}