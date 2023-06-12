package com.stefang.dev.core.data

import com.stefang.dev.core.data.database.ArithmeticDao
import com.stefang.dev.core.data.datastore.ArithmeticDataStore
import com.stefang.dev.core.data.file.FileDataSource
import com.stefang.dev.core.data.model.ArithmeticModel
import com.stefang.dev.core.data.model.toDbModel
import com.stefang.dev.core.data.model.toFileModel
import com.stefang.dev.core.data.model.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class ArithmeticHistoryRepositoryImpl @Inject constructor(
    private val arithmeticDao: ArithmeticDao,
    private val fileDataSource: FileDataSource,
    private val dataStore: ArithmeticDataStore
) : ArithmeticHistoryRepository {
    override val histories: Flow<List<ArithmeticModel>>
        get() = dataStore.storage.flatMapLatest { storage ->
            if (storage == ArithmeticDataStore.Storage.File) {
                flow { emit(fileDataSource.getAllHistories().map { it.toModel() }) }
            } else {
                arithmeticDao.getAllHistories().map { list ->
                    list.map { it.toModel() }
                }
            }
        }

    override suspend fun recordHistory(
        model: ArithmeticModel,
        createdAt: Long
    ): Unit = withContext(Dispatchers.IO) {
        dataStore.storage.firstOrNull()?.let {
            if (it == ArithmeticDataStore.Storage.File) {
                fileDataSource.recordHistory(model.toFileModel(createdAt))
            } else {
                arithmeticDao.insertHistory(model.toDbModel(createdAt))
            }
        }
    }
}
