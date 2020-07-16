package com.thanosfisherman.domain.common

sealed class NetworkResultState<out T : Any> {

    object Loading : NetworkResultState<Nothing>()

    data class Success<T : Any>(val data: T) : NetworkResultState<T>()

    data class Error(val error: String) : NetworkResultState<Nothing>()
}