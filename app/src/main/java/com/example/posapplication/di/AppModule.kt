package com.example.posapplication.di

import org.koin.dsl.module

val appModule
    get() =
        module {
            includes(
                com.example.posapplication.core.sharepreference.sharedPreferences,
                databaseModule,
                networkModule,
            )
        }
