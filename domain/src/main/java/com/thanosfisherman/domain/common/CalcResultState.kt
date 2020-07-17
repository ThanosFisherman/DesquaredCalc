package com.thanosfisherman.domain.common

sealed class CalcResultState<out T : Any> {
    object ClearAll : CalcResultState<Nothing>()
    object ShowConversionDialog : CalcResultState<Nothing>()
    data class SuccessDigit<T : Any>(val data: T) : CalcResultState<T>()
    data class SuccessEquals<T : Any>(val data: T) : CalcResultState<T>()
    data class Error(val errorMsg: String) : CalcResultState<Nothing>()
}