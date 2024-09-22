package com.example.posapplication.core.confiig

import android.util.Log
import com.example.posapplication.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashlyticsTree : Timber.Tree() {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        throwable: Throwable?,
    ) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }

        if (BuildConfig.DEBUG) {
            crashlytics.isCrashlyticsCollectionEnabled = false
            return
        }

        crashlytics.setCustomKey(CRASHLYTICS_KEY_PRIORITY, priority)

        if (tag != null) {
            crashlytics.setCustomKey(CRASHLYTICS_KEY_TAG, tag)
        }
        crashlytics.setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)

        if (throwable == null) {
            crashlytics.recordException(Exception(message))
        } else {
            crashlytics.recordException(throwable)
        }
    }

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"
    }
}
