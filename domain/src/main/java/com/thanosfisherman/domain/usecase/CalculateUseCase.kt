package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.CalcResultState
import com.thanosfisherman.domain.enums.PadType
import com.thanosfisherman.domain.expressions.Expressions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.math.RoundingMode

@ExperimentalCoroutinesApi
class CalculateUseCase : BaseUseCase<PadType, Unit>() {

    private val builder = StringBuilder()

    private var isEqualsPressed = false
    override fun execute(params: PadType) {
        val expressionResult = Expressions.setRoundingMode(RoundingMode.UP).setPrecision(20)
        if (isEqualsPressed) {
            builder.clear()
            isEqualsPressed = false
        }
        when (params) {
            PadType.CLEAR -> builder.clear()
            PadType.EQUALS -> {
                isEqualsPressed = true
                val result = expressionResult.evalToString(builder.toString().replace("mod", "m", true))
                builder.clear()
                builder.append(result)
            }
            PadType.SQRT -> builder.append("sqrt(")
            PadType.LOG -> builder.append("log(")
            PadType.LN -> builder.append("ln(")
            PadType.PI -> builder.append("pi")
            PadType.MOD -> builder.append("mod")
            else -> {
                builder.append(params.type)
            }
        }
        stateResult.value = CalcResultState.Success(builder.toString())
    }
}