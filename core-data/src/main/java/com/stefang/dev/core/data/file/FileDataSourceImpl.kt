package com.stefang.dev.core.data.file

import android.content.Context
import com.google.gson.Gson
import java.io.File
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val cryptoFile: CryptoFile,
    private val context: Context,
    private val gson: Gson
) : FileDataSource {

    private val fileName = "scan_me_arithmetic_data.txt"

    override fun getAllHistories(): List<ArithmeticFile> {
        val encryptedData = readFile()
        return if (encryptedData.isBlank()) {
            emptyList()
        } else {
            val decryptedData = cryptoFile.decryptData(encryptedData)
            parseData(decryptedData)
        }
    }

    override fun recordHistory(model: ArithmeticFile) {
        val existingData = getAllHistories().toMutableList()
        existingData.add(model)
        val plainData = formatData(existingData)
        val encryptedData = cryptoFile.encryptData(plainData)
        writeFile(encryptedData)
    }

    private fun readFile(): String {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            file.readText()
        } else {
            ""
        }
    }

    private fun writeFile(data: String) {
        val file = File(context.filesDir, fileName)
        file.writeText(data)
    }

    private fun parseData(data: String): List<ArithmeticFile> {
        return gson.fromJson(data, Array<ArithmeticFile>::class.java).toList()
    }

    private fun formatData(data: List<ArithmeticFile>): String {
        return gson.toJson(data)
    }
}
