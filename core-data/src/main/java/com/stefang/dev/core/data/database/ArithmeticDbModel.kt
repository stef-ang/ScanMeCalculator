package com.stefang.dev.core.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arithmetic")
data class ArithmeticDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "operand_1") val operand1: Double,
    @ColumnInfo(name = "operand_2") val operand2: Double,
    val operator: Char,
    val result: Double,
    val uri: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {

    constructor(
        operand1: Double,
        operand2: Double,
        operator: Char,
        result: Double,
        uri: String,
        createdAt: Long
    ) : this (0, operand1, operand2, operator, result, uri, createdAt)
}
