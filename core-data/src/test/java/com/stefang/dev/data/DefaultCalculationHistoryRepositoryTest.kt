/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stefang.dev.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.stefang.dev.core.data.DefaultCalculationHistoryRepository
import com.stefang.dev.core.database.CalculationHistory
import com.stefang.dev.core.database.CalculationHistoryDao

/**
 * Unit tests for [DefaultCalculationHistoryRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultCalculationHistoryRepositoryTest {

    @Test
    fun calculationHistorys_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultCalculationHistoryRepository(FakeCalculationHistoryDao())

        repository.add("Repository")

        assertEquals(repository.calculationHistorys.first().size, 1)
    }

}

private class FakeCalculationHistoryDao : CalculationHistoryDao {

    private val data = mutableListOf<CalculationHistory>()

    override fun getCalculationHistorys(): Flow<List<CalculationHistory>> = flow {
        emit(data)
    }

    override suspend fun insertCalculationHistory(item: CalculationHistory) {
        data.add(0, item)
    }
}
