package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.CalcResultState
import com.thanosfisherman.domain.common.cancelIfActive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
abstract class BaseUseCase<in Params, out Type> {

    private var job: Job? = null
    val stateResult: MutableStateFlow<CalcResultState<String>> = MutableStateFlow(CalcResultState.Success(""))
    val stateExpresion: MutableStateFlow<CalcResultState<String>> = MutableStateFlow(CalcResultState.Success(""))

    abstract fun execute(params: Params): Type

    operator fun invoke(scope: CoroutineScope, params: Params) {
        job?.cancelIfActive()
        job = scope.launch { execute(params) }
    }
}

