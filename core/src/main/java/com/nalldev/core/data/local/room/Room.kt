package com.nalldev.core.data.local.room

import android.app.Application
import androidx.room.Room
import com.nalldev.core.data.local.room.dao.JobsDao
import com.nalldev.core.data.local.room.dao.JobFavoritesDao
import com.nalldev.core.data.local.room.dao.RemoteKeysDao
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

fun provideDatabase(application: Application, factory: SupportFactory): JobDb =
    Room.databaseBuilder(
        application,
        JobDb::class.java,
        "job_db"
    )
        .fallbackToDestructiveMigration()
        .openHelperFactory(factory)
        .build()

fun provideSupportFactory() : SupportFactory {
    val passphrase: ByteArray = SQLiteDatabase.getBytes("gombal".toCharArray())
    return SupportFactory(passphrase)
}

fun provideJobsDao(database: JobDb): JobsDao = database.jobsDao()
fun provideJobFavoritesDao(jobDb: JobDb): JobFavoritesDao = jobDb.jobFavoritesDao()
fun provideRemoteKeysDao(jobDb: JobDb): RemoteKeysDao = jobDb.remoteKeysDao()