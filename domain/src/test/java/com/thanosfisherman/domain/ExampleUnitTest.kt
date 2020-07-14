package com.thanosfisherman.domain

import com.thanosfisherman.domain.expressions.Expressions
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.RoundingMode

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testExpressionParser() {
        val result = Expressions.setRoundingMode(RoundingMode.UP)
            .setPrecision(10)
            .evalToString("log(4)")

        println(result)
    }
}