package com.stefang.dev.core.data.file

data class ArithmeticFile(
    val firstOperand: Double,
    val secondOperand: Double,
    val operator: Char,
    val result: Double,
    val uri: String,
    val createdAt: Long
)
