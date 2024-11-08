package com.nalldev.home.data.di

import com.nalldev.home.data.network.ApiServices
import com.nalldev.home.data.repositories.JobRepositoryImpl
import com.nalldev.home.domain.repositories.JobRepository
import org.koin.dsl.module

val dataModule = module {
    single<JobRepository> { JobRepositoryImpl(get()) }
    single { ApiServices(get()) }
}