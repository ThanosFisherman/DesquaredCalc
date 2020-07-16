package com.thanosfisherman.presentation.di

import com.thanosfisherman.presentation.viewModel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@FlowPreview
@ExperimentalCoroutinesApi
val presentationModule = module {

    viewModel { MainViewModel(get(), get()) }

}