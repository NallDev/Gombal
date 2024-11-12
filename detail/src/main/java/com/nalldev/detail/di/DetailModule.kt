package com.nalldev.detail.di

import com.nalldev.detail.DetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { DetailViewModel(get()) }
}