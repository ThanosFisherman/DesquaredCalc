package com.thanosfisherman.domain.common

import com.thanosfisherman.domain.models.ExchangeModel
import com.thanosfisherman.domain.repos.NetworkRepo
import com.thanosfisherman.domain.usecase.BaseUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ExchangeUseCase(private val networkRepo: NetworkRepo) : BaseUseCase<List<String>, NetworkResultState<ExchangeModel>>() {
    override suspend fun execute(params: List<String>) {
        channelResult.offer(NetworkResultState.Loading)
        try {
            val result = networkRepo.getExchangeRates(params[0], params[1])
            channelResult.offer(NetworkResultState.Success(result))
        } catch (e: Exception) {
            channelResult.offer(NetworkResultState.Error(e.message.toString()))
        }
    }
}