package com.thanosfisherman.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thanosfisherman.domain.usecase.ExchangeUseCase
import com.thanosfisherman.domain.enums.PadType
import com.thanosfisherman.domain.usecase.CalculateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel(private val calculateUseCase: CalculateUseCase, private val exchangeUseCase: ExchangeUseCase) : ViewModel() {

    val liveCalculateStateResult = calculateUseCase.channelResult.asFlow().asLiveData()
    val liveNetworkStateResult = exchangeUseCase.channelResult.asFlow().asLiveData()

    fun addExpression(padType: PadType) {
        calculateUseCase(viewModelScope, padType)
    }

    fun exchange(fromSymbol: String, toSymbol: String, amount: String) {
        exchangeUseCase(viewModelScope, listOf(fromSymbol, toSymbol, amount))
    }
}