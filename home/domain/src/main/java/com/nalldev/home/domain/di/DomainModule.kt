package com.nalldev.home.domain.di

import com.nalldev.home.domain.usecase.GetJobs
import com.nalldev.home.domain.usecase.JobUseCases
import org.koin.dsl.module

val domainModule = module{
    single { JobUseCases(get()) }
    single { GetJobs(get()) }
}