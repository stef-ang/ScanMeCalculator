package com.stefang.dev.core.data

import com.stefang.dev.core.data.database.ArithmeticDao
import com.stefang.dev.core.data.file.FileDataSource
import com.stefang.dev.core.data.mapper.toDbModel
import com.stefang.dev.core.data.mapper.toFileModel
import com.stefang.dev.core.data.mapper.toModel
import com.stefang.dev.core.data.model.ArithmeticModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArithmeticHistoryRepositoryImpl @Inject constructor(
    private val arithmeticDao: ArithmeticDao,
    private val fileDataSource: FileDataSource
) : ArithmeticHistoryRepository {

//    override val histories: Flow<List<ArithmeticModel>>
//        get() = arithmeticDao.getAllHistories().map { list ->
//            list.map { it.toModel() }
//        }
    override val histories: Flow<List<ArithmeticModel>> = flow {
            emit(fileDataSource.getAllHistories().map { it.toModel() })
    }

    override suspend fun recordHistory(
        model: ArithmeticModel,
        createdAt: Long
    ): Unit = withContext(Dispatchers.IO) {
//        arithmeticDao.insertHistory(model.toDbModel(createdAt))
        fileDataSource.recordHistory(model.toFileModel(createdAt))
    }
}
