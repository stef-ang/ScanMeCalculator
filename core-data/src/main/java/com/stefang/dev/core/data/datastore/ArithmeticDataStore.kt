package com.stefang.dev.core.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ArithmeticDataStore {

    val storage: Flow<Storage>

    suspend fun setStorage(storage: Storage)

    enum class Storage { File, Database }
}

class ArithmeticDataStoreImpl @Inject constructor(val context: Context) : ArithmeticDataStore {

    private val Context.dataStore by preferencesDataStore("arithmetic_data_store")

    private val storageKey = stringPreferencesKey("arithmetic_history_key")

    override val storage: Flow<ArithmeticDataStore.Storage> =
        context.dataStore.data.map { preferences ->
            if (preferences[storageKey] == "File") {
                ArithmeticDataStore.Storage.File
            } else {
                ArithmeticDataStore.Storage.Database
            }
        }

    override suspend fun setStorage(storage: ArithmeticDataStore.Storage) {
        context.dataStore.edit { preferences ->
            if (storage == ArithmeticDataStore.Storage.File) {
                preferences[storageKey] = "File"
            } else {
                preferences[storageKey] = "Database"
            }
        }
    }
}
