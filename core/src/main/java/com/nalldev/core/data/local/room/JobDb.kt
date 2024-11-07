package com.nalldev.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JobEntity::class], version = 1)
abstract class JobDb : RoomDatabase() {

}