package com.thanosfisherman.desquaredcalc

import android.app.Application
import timber.log.Timber

class DesquaredCalcApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}