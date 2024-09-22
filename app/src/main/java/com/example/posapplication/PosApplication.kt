package com.example.posapplication

import android.app.Application
import com.example.posapplication.core.confiig.CrashlyticsTree
import timber.log.Timber

class PosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() =
        when {
            BuildConfig.DEBUG -> Timber.plant(Timber.DebugTree())
            else -> Timber.plant(CrashlyticsTree())
        }

    private fun initKoin() {
        TODO("Not implemented yet")
    }
}
