package com.thanosfisherman.domain.expressions.internal

import java.math.BigDecimal

abstract class Function {

    abstract fun call(arguments: List<BigDecimal>): BigDecimal

}