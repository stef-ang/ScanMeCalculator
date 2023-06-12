package com.stefang.dev.feature.calculator.model

data class ArithmeticData(
    val firstOperand: Double,
    val secondOperand: Double,
    val operator: Char,
    val result: Double
) {
    fun getInput(): String {
        return firstOperand.toStringAdaptively() + operator + secondOperand.toStringAdaptively()
    }

    fun getResult(): String = result.toStringAdaptively()

    private fun Double.toStringAdaptively(): String {
        return if (isRoundedNumber()) {
            toInt().toString()
        } else {
            toString()
        }
    }

    private fun Double.isRoundedNumber(): Boolean {
        return isFinite() && toInt().toDouble() == this
    }
}
