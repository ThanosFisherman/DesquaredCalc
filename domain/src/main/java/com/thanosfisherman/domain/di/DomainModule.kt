package com.thanosfisherman.domain.di

import com.thanosfisherman.domain.usecase.CalculateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val domainModule = module {
    single { CalculateUseCase() }
}
