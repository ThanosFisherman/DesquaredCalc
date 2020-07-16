package com.thanosfisherman.data_network.api

import com.thanosfisherman.data_network.models.ExchangeDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface Exchangeratesapi {

    @GET("latest")
    suspend fun getExchangeRate(@Query(value = "base") base: String, @Query(value = "symbols") symbol: String): ExchangeDataModel
}