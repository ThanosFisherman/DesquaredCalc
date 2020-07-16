package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.CalcResultState
import com.thanosfisherman.domain.enums.PadType
import com.thanosfisherman.domain.expressions.Expressions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.math.RoundingMode

@ExperimentalCoroutinesApi
class CalculateUseCase : BaseUseCase<PadType, CalcResultState<String>>() {

    private val builder = StringBuilder()

    private var isEqualsPressed = false
    override suspend fun execute(params: PadType) {
        val expressionResult = Expressions.setRoundingMode(RoundingMode.UP).setPrecision(20)

        when (params) {
            PadType.CLEAR -> {
                if (isEqualsPressed) {
                    isEqualsPressed = false
                    builder.clear()
                    channelResult.offer(CalcResultState.ClearAll)
                    return
                } else {
                    if (builder.isNotEmpty())
                        builder.deleteCharAt(builder.length - 1)
                }
            }
            PadType.CLEAR_ALL -> {
                builder.clear()
                channelResult.offer(CalcResultState.ClearAll)
                return
            }
            PadType.EQUALS -> {
                if (builder.isBlank()) {
                    return
                }
                isEqualsPressed = true

                try {
                    val result = expressionResult.evalToString(builder.toString().replace("mod", "m", true))
                    Timber.i("RESULT $result")
                    builder.clear()
                    builder.append(result)
                } catch (e: Throwable) {
                    Timber.i("ERROR")
                    channelResult.offer(CalcResultState.Error(e.cause?.message ?: e.message ?: "unknown error"))
                    builder.clear()
                    return
                }
            }
            PadType.SQRT -> builder.append("sqrt(")
            PadType.LOG -> builder.append("log(")
            PadType.LN -> builder.append("ln(")
            PadType.PI -> builder.append("pi")
            PadType.MOD -> builder.append("mod")
            PadType.PLUS -> {
                isEqualsPressed = false
                builder.append(params.type)
            }
            PadType.MINUS -> {
                isEqualsPressed = false
                builder.append(params.type)
            }
            PadType.MULTIPLY -> {
                isEqualsPressed = false
                builder.append(params.type)
            }
            PadType.DIVIDE -> {
                isEqualsPressed = false
                builder.append(params.type)
            }
            else -> {
                if (isEqualsPressed) {
                    builder.clear()
                    isEqualsPressed = false
                }
                builder.append(params.type)
            }
        }
        if (isEqualsPressed) {
            channelResult.offer(CalcResultState.SuccessEquals(builder.toString()))
        } else {
            channelResult.offer(CalcResultState.SuccessDigit(builder.toString()))
        }
    }
}