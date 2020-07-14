package com.thanosfisherman.domain.expressions.internal

import java.math.BigDecimal

interface Function {

    fun call(arguments: List<BigDecimal>): BigDecimal

}