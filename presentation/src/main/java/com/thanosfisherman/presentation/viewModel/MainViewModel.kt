package com.thanosfisherman.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanosfisherman.domain.common.asStateFlow
import com.thanosfisherman.domain.enums.PadType
import com.thanosfisherman.domain.usecase.CalculateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val calculateUseCase: CalculateUseCase) : ViewModel() {

    val stateResult = calculateUseCase.stateResult.asStateFlow()

    fun addExpression(padType: PadType) {
        calculateUseCase(viewModelScope, padType)
    }
}