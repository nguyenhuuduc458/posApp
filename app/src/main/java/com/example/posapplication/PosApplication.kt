package com.example.posapplication

import android.app.Application
import com.example.posapplication.core.confiig.CrashlyticsTree
import com.example.posapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() =
        when {
            BuildConfig.DEBUG -> Timber.plant(Timber.DebugTree())
            else -> Timber.plant(CrashlyticsTree())
        }

    private fun initKoin() {
        startKoin {
            startKoin {
                androidContext(this@PosApplication)
                modules(appModule)
            }
        }
    }
}
