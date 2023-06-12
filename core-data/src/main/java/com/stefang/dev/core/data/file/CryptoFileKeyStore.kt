package com.stefang.dev.core.data.file

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import com.google.gson.Gson
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class CryptoFileKeyStore @Inject constructor(
    private val gson: Gson
) : CryptoFile {

    private val TAG = "CryptoFile"

    companion object {
        private const val KEY_SIZE = 256
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        private const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val TLEN = 128
        private const val CHAR_ENCODING = "UTF-8"

        private const val SECRET_KEY_NAME = "com.stefang.dev.core.data.file.key"
    }

    private fun getInitializedCipherForEncryption(): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher
    }

    private fun getInitializedCipherForDecryption(initializationVector: ByteArray): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey()
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(TLEN, initializationVector))
        return cipher
    }

    private fun getCipher(): Cipher {
        val transformation = "$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING"
        return Cipher.getInstance(transformation)
    }

    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        // .load(null) to create an empty Keystore, Keystore must be loaded before it can be accessed
        keyStore.load(null)
        keyStore.getKey(SECRET_KEY_NAME, null)?.let {
            // If SecretKey was previously created for that KEY_NAME, then grab and return it.
            Log.d(TAG, "SecretKey: reusing KeyStore")
            return it as SecretKey
        }

        // if you reach here, then a new SecretKey must be generated for that keyName
        val paramsBuilder = KeyGenParameterSpec.Builder(
            SECRET_KEY_NAME,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
        paramsBuilder.apply {
            setBlockModes(ENCRYPTION_BLOCK_MODE)
            setEncryptionPaddings(ENCRYPTION_PADDING)
            setKeySize(KEY_SIZE)
            setUserAuthenticationRequired(false)
        }

        val keyGenParams = paramsBuilder.build()
        val keyGenerator = KeyGenerator.getInstance(
            ENCRYPTION_ALGORITHM,
            ANDROID_KEYSTORE
        )
        keyGenerator.init(keyGenParams)
        Log.d(TAG, "SecretKey: NEW KeyStore")
        return keyGenerator.generateKey()
    }

    override fun encryptData(data: String): String {
        val cipher = getInitializedCipherForEncryption()
        val encryptedText = cipher.doFinal(data.toByteArray(Charset.forName(CHAR_ENCODING)))
        return gson.toJson(CiphertextWrapper(encryptedText, cipher.iv))
    }

    override fun decryptData(data: String): String {
        val ciphertextWrapper = gson.fromJson(data, CiphertextWrapper::class.java)
        val cipher = getInitializedCipherForDecryption(ciphertextWrapper.initializationVector)
        val plaintext = cipher.doFinal(ciphertextWrapper.ciphertext)
        return String(plaintext, Charset.forName(CHAR_ENCODING))
    }
}
