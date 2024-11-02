package com.example.posapplication.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
//    @Provides
//    @Singleton
//    fun provideNoteDatabase(
//        @ApplicationContext
//        context: Context,
//    ): NoteDatabase =
//        Room
//            .databaseBuilder(
//                context.applicationContext,
//                NoteDatabase::class.java,
//                "note_database",
//            ).build()
//
//    @Provides
//    @Singleton
//    fun provideNoteDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()
//
//    @Provides
//    @Singleton
//    fun provideAccountDao(noteDatabase: NoteDatabase) = noteDatabase.accountDao()
}
