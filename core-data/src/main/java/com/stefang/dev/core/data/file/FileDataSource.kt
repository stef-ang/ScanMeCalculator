package com.stefang.dev.core.data.file

interface FileDataSource {

    fun getAllHistories(): List<ArithmeticFile>

    fun recordHistory(model: ArithmeticFile)
}
