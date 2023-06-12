package com.stefang.dev.feature.calculator

import com.stefang.dev.feature.calculator.model.ArithmeticData
import javax.inject.Inject

class ArithmeticExtractorUseCase @Inject constructor() {

    fun extract(input: String): ArithmeticData? {
        // it supports '+', '-', '*', '/'
//        val regex = Regex("""\b(\d+(\.\d+)?)\s*([-+*/])\s*(\d+(\.\d+)?)\b""")

        // it also supports 'x' and 'X' as '*'
        val regex = Regex("""\b(\d+(\.\d+)?)\s*([-+*/xX])\s*(\d+(\.\d+)?)\b""")

        val matchResult = regex.find(input)
        if (matchResult != null) {

            var first: Double? = null
            var operator: Char? = null
            var second: Double? = null

            for (item in matchResult.groupValues) {
                if (first == null && item.toDoubleOrNull() != null) {
                    first = item.toDouble()
                } else if (first != null && operator == null && item.length == 1 && item[0] in listOf('+', '-', '*', '/')) {
                    operator = item[0]
                } else if (first != null && operator != null && item.toDoubleOrNull() != null) {
                    second = item.toDouble()
                    break
                }
            }

            if (first == null || operator == null || second == null) return null

            if(operator == 'x' || operator == 'X') operator = '*'

            val result = calculate(first, second, operator)
            return ArithmeticData(
                firstOperand = first,
                secondOperand = second,
                operator = operator,
                result = result
            )
        }
        return null
    }

    private fun calculate(numb1: Double, numb2: Double, operator: Char): Double {
        if (numb2 == 0.0 && operator == '/') return 0.0
        return when (operator) {
            '+' -> numb1 + numb2
            '-' -> numb1 - numb2
            '*' -> numb1 * numb2
            '/' -> numb1 / numb2
            else -> 0.0
        }
    }
}
