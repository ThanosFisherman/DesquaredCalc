package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.cancelIfActive
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel


@ExperimentalCoroutinesApi
abstract class BaseUseCase<in Params, State : Any> {

    private var job: Job? = null
    val channelResult: ConflatedBroadcastChannel<State> = ConflatedBroadcastChannel()

    abstract suspend fun execute(params: Params)

    operator fun invoke(scope: CoroutineScope, params: Params) {
        job?.cancelIfActive()
        job = scope.launch(Dispatchers.IO) { execute(params) }
    }
}

