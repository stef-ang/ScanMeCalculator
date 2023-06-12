package com.stefang.dev.core.data.file

interface CryptoFile {

    fun encryptData(data: String): String

    fun decryptData(data: String): String
}
