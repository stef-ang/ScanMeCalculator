package com.stefang.dev.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    exportSchema = true,
    version = 1,
    entities = [ArithmeticDbModel::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun arithmeticDao(): ArithmeticDao
}
