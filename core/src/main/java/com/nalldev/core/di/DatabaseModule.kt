package com.nalldev.core.di

import android.app.Application
import androidx.room.Room
import com.nalldev.core.data.local.room.JobDb
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(get()) }
}

fun provideDatabase(application: Application): JobDb =
    Room.databaseBuilder(
        application,
        JobDb::class.java,
        "job_db"
    )
        .fallbackToDestructiveMigration()
        .build()