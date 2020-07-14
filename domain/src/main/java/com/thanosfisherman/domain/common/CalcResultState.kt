package com.thanosfisherman.domain.common

sealed class CalcResultState<out T : Any> {
    object Loading : CalcResultState<Nothing>()
    data class Success<T : Any>(val data: T) : CalcResultState<T>()
    data class Error(val errorMsg: String) : CalcResultState<Nothing>()
}