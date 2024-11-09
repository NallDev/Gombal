package com.nalldev.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nalldev.core.data.local.room.dao.JobsDao
import com.nalldev.core.data.local.room.dao.JobFavoritesDao
import com.nalldev.core.data.local.room.dao.RemoteKeysDao
import com.nalldev.core.data.local.room.model.JobEntity
import com.nalldev.core.data.local.room.model.JobFavoritesEntity
import com.nalldev.core.data.local.room.model.RemoteKeys
import com.nalldev.core.utils.Converters

@Database(entities = [JobEntity::class, JobFavoritesEntity::class, RemoteKeys::class], version = 1)
@TypeConverters(Converters::class)
abstract class JobDb : RoomDatabase() {
    abstract fun jobsDao(): JobsDao
    abstract fun jobFavoritesDao(): JobFavoritesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}