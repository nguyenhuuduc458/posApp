package com.example.posapplication.core.sharepreference

import android.content.SharedPreferences
import com.example.posapplication.core.cryptographic.KeyStoreUtil
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

object SecureSharePreferenceUtil : KoinComponent {
    init {
        KeyStoreUtil.generateKeyPair(KeyStoreUtil.KEY_ALIAS)
    }

    private val secureSharedPreferences: SharedPreferences by inject(qualifier = named("secure_prefs"))
    var accessToken: String by SecureSharePreferenceDelegates(secureSharedPreferences, "accessToken", "", "KEY_ALIAS")
}
