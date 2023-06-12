package com.stefang.dev.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArithmeticDao {

    @Query("SELECT * FROM arithmetic ORDER BY created_at DESC")
    fun getAllHistories(): Flow<List<ArithmeticDbModel>>

    @Insert
    fun insertHistory(arithmetic: ArithmeticDbModel)
}
