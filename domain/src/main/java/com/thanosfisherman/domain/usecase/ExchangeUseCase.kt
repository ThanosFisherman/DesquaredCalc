package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.models.ConversionResultModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ExchangeUseCase(private val networkRepo: NetworkRepo) : BaseUseCase<List<String>, NetworkResultState<ConversionResultModel>>() {
    override suspend fun execute(params: List<String>) {
        channelResult.offer(NetworkResultState.Loading)
        try {
            val result = networkRepo.getExchangeRates(params[0], params[1])
            val fromCurrency = params[0]
            val toCurrency = params[1]
            try {
                val rate = result.rates[toCurrency] ?: throw NumberFormatException("rate is null")
                val amount = if (params[2].isBlank()) 0.toDouble() else params[2].toDouble()
                val conversionResult = rate * amount
                channelResult.offer(
                    NetworkResultState.Success(
                        ConversionResultModel(
                            fromCurrency,
                            toCurrency,
                            conversionResult
                        )
                    )
                )
            } catch (e: NumberFormatException) {
                channelResult.offer(NetworkResultState.Error(e.message.toString()))
                return
            }
        } catch (e: Exception) {
            channelResult.offer(NetworkResultState.Error(e.message.toString()))
        }
    }
}