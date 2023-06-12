package com.stefang.dev.feature.calculator.mapper

import android.net.Uri
import com.stefang.dev.core.data.model.ArithmeticModel
import com.stefang.dev.feature.calculator.ArithmeticData

fun ArithmeticData.toModel(uri: Uri) = ArithmeticModel(
    firstOperand = firstOperand,
    secondOperand = secondOperand,
    operator = operator,
    result = result,
    uri = uri
)

fun ArithmeticModel.toUiModel() = ArithmeticData(
    firstOperand = firstOperand,
    secondOperand = secondOperand,
    operator = operator,
    result = result,
)
