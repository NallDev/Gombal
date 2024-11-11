package com.nalldev.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nalldev.core.data.local.room.model.JobFavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobFavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobFavoritesEntity)

    @Query("SELECT * FROM job_favorites")
    fun getJobs() : Flow<List<JobFavoritesEntity>>

    @Query("DELETE FROM job_favorites WHERE id = :id")
    suspend fun deleteJob(id: String)

}