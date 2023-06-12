package com.stefang.dev.core.data.model

import android.net.Uri

data class ArithmeticModel(
    val firstOperand: Double,
    val secondOperand: Double,
    val operator: Char,
    val result: Double,
    val uri: Uri
)
