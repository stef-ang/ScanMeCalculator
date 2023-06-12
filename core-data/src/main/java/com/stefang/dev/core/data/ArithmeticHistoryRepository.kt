package com.stefang.dev.core.data

import com.stefang.dev.core.data.model.ArithmeticModel
import kotlinx.coroutines.flow.Flow

interface ArithmeticHistoryRepository {

    val histories: Flow<List<ArithmeticModel>>

    suspend fun recordHistory(model: ArithmeticModel, createdAt: Long)
}
