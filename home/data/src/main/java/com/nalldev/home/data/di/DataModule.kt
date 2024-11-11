package com.nalldev.home.data.di

import com.nalldev.home.data.datasource.LocalDataSource
import com.nalldev.home.data.datasource.NetworkDataSource
import com.nalldev.home.data.network.ApiServices
import com.nalldev.home.data.repositories.JobRepositoryImpl
import com.nalldev.home.domain.repositories.JobRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single { ApiServices(get()) }
    single { NetworkDataSource(get(), get(named("IODispatcher"))) }
    single { LocalDataSource(get(), get(named("IODispatcher"))) }

    single<JobRepository> { JobRepositoryImpl(get(), get()) }
}