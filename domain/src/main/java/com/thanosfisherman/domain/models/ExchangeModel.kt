package com.thanosfisherman.domain.models

data class ExchangeModel(
    val rates: Map<String, Double>,
    val base: String,
    val date: String
)