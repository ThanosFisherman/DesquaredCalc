package com.thanosfisherman.data_network.models

import com.thanosfisherman.domain.common.DomainMappable
import com.thanosfisherman.domain.models.ExchangeModel

data class ExchangeDataModel(
    val rates: Map<String, Double>,
    val base: String,
    val date: String
) : DomainMappable<ExchangeModel> {
    override fun asDomain(): ExchangeModel {
        return ExchangeModel(rates, base, date)
    }
}