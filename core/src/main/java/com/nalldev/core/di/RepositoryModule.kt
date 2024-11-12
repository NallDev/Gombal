package com.nalldev.core.di

import com.nalldev.core.data.repositories.DarkModeRepositoryImpl
import com.nalldev.core.data.repositories.OnBoardingRepositoryImpl
import com.nalldev.core.domain.repositories.DarkModeRepository
import com.nalldev.core.domain.repositories.OnBoardingRepository
import org.koin.dsl.module

val repositoryModule = module {
    single <DarkModeRepository>{ DarkModeRepositoryImpl(get()) }
    single <OnBoardingRepository>{
        OnBoardingRepositoryImpl(
            get()
        )
    }
}