package com.thanosfisherman.domain.usecase


abstract class BaseUseCase<in Params, out Type> {

    abstract fun execute(params: Params): Type

}

