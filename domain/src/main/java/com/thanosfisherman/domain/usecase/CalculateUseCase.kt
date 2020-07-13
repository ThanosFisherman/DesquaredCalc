package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.expressions.Expressions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.math.RoundingMode

@ExperimentalCoroutinesApi
class CalculateUseCase()  {
    private val calculateState = MutableStateFlow<String>("")
    private val builder = StringBuilder()
     fun execute(params: String){
        calculateState.value =
        Expressions.setPrecision(50)
            .setRoundingMode(RoundingMode.UP)
            .evalToString(params)
    }
}