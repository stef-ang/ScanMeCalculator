package com.stefang.dev.core.data.model

import android.net.Uri
import com.stefang.dev.core.data.database.ArithmeticDbModel
import com.stefang.dev.core.data.file.ArithmeticFile
import java.net.URLDecoder
import java.net.URLEncoder

fun ArithmeticDbModel.toModel() = ArithmeticModel(
    firstOperand = operand1,
    secondOperand = operand2,
    operator = operator,
    result = result,
    uri = uri.decode()
)

fun ArithmeticModel.toDbModel(createdAt: Long) = ArithmeticDbModel(
    operand1 = firstOperand,
    operand2 = secondOperand,
    operator = operator,
    result = result,
    uri = uri.encode(),
    createdAt = createdAt
)

fun ArithmeticFile.toModel() = ArithmeticModel(
    firstOperand = firstOperand,
    secondOperand = secondOperand,
    operator = operator,
    result = result,
    uri = uri.decode()
)

fun ArithmeticModel.toFileModel(createdAt: Long) = ArithmeticFile(
    firstOperand = firstOperand,
    secondOperand = secondOperand,
    operator = operator,
    result = result,
    uri = uri.encode(),
    createdAt = createdAt
)

fun Uri.encode(): String {
    return URLEncoder.encode(this.toString(), "UTF-8")
}

fun String.decode(): Uri {
    val decodedString = URLDecoder.decode(this, "UTF-8")
    return Uri.parse(decodedString)
}
