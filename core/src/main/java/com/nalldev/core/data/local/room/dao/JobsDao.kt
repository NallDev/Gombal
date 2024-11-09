package com.nalldev.core.data.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nalldev.core.data.local.room.model.JobEntity

@Dao
interface JobsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<JobEntity>)

    @Query("SELECT * FROM jobs")
    fun getJobs(): PagingSource<Int, JobEntity>

    @Query("DELETE FROM jobs")
    suspend fun deleteJobs()
}