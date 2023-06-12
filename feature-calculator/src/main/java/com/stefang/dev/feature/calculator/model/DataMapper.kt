package com.stefang.dev.feature.calculator.model

import android.net.Uri
import com.stefang.dev.core.data.datastore.ArithmeticDataStore
import com.stefang.dev.core.data.model.ArithmeticModel

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

fun UsedStorage.toModel() = if (this == UsedStorage.File) {
    ArithmeticDataStore.Storage.File
} else {
    ArithmeticDataStore.Storage.Database
}

fun ArithmeticDataStore.Storage.toUiModel() = if (this == ArithmeticDataStore.Storage.File) {
    UsedStorage.File
} else {
    UsedStorage.Database
}
