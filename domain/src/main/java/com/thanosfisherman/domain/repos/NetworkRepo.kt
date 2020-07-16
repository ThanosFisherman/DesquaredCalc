package com.thanosfisherman.domain.repos

import com.thanosfisherman.domain.models.ExchangeModel

interface NetworkRepo {

    suspend fun getExchangeRates(base: String, symbol: String): ExchangeModel
}