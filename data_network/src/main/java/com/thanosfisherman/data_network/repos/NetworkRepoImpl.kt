package com.thanosfisherman.data_network.repos

import com.thanosfisherman.data_network.api.Exchangeratesapi
import com.thanosfisherman.domain.models.ExchangeModel
import com.thanosfisherman.domain.repos.NetworkRepo

class NetworkRepoImpl(private val exchangeratesapi: Exchangeratesapi) : NetworkRepo {
    override suspend fun getExchangeRates(base: String, symbol: String): ExchangeModel {
        return exchangeratesapi.getExchangeRate(base, symbol).asDomain()
    }
}