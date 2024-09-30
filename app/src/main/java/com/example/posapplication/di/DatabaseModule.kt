package com.example.posapplication.di

import org.koin.dsl.module

val databaseModule get() =
    module {
//        single {
//            Room
//                .databaseBuilder(
//                    get<Application>(),
//                    PosDatabase::class.java,
//                    "PosDatabase",
//                ).build()
//        }
    }
