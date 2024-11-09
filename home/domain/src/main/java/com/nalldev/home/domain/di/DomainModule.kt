package com.nalldev.home.domain.di

import com.nalldev.home.domain.usecase.DeleteJobFromFavorite
import com.nalldev.home.domain.usecase.GetJobs
import com.nalldev.home.domain.usecase.InsertJobToFavorite
import com.nalldev.home.domain.usecase.JobUseCases
import org.koin.dsl.module

val domainModule = module{
    single { GetJobs(get()) }
    single { InsertJobToFavorite(get()) }
    single { DeleteJobFromFavorite(get()) }

    single { JobUseCases(get(), get(), get()) }
}