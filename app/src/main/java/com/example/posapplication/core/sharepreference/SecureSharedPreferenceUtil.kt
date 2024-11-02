package com.example.posapplication.core.sharepreference

import android.content.SharedPreferences
import com.example.note.core.sharepreference.AuthPreferences
import com.example.note.core.sharepreference.SecureSharePreferenceDelegates
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureSharedPreferenceUtil
    @Inject
    constructor(
        @AuthPreferences private val secureSharePreferenceDelegates: SharedPreferences,
    ) {
        val accessToken by SecureSharePreferenceDelegates(
            secureSharePreferenceDelegates,
            "accessToken",
            "",
            "token",
        )
        val expiresIn by SecureSharePreferenceDelegates(
            secureSharePreferenceDelegates,
            "expiresIn",
            "",
            "expiresIn",
        )
    }
