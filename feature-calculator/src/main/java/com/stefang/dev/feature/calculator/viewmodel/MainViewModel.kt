package com.stefang.dev.feature.calculator.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefang.dev.core.data.ArithmeticHistoryRepository
import com.stefang.dev.core.data.datastore.ArithmeticDataStore
import com.stefang.dev.feature.calculator.model.ArithmeticData
import com.stefang.dev.feature.calculator.model.UsedStorage
import com.stefang.dev.feature.calculator.model.toModel
import com.stefang.dev.feature.calculator.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: ArithmeticHistoryRepository,
    private val dataStore: ArithmeticDataStore
): ViewModel() {

    init {
        viewModelScope.launch {
            if (dataStore.storage.firstOrNull() == null) {
                // set default storage
                dataStore.setStorage(ArithmeticDataStore.Storage.Database)
            }
        }
    }

    val storageType: StateFlow<UsedStorage> = dataStore.storage.map {
        it.toUiModel()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UsedStorage.Database
    )

    val allHistories: StateFlow<List<ArithmeticData>> = repository.histories.catch {
        Log.d("error_scan_me", it.message ?: "error not found")
        emit(emptyList())
    }.map { list ->
        list.map { it.toUiModel() }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun setStorage(storage: UsedStorage) = viewModelScope.launch {
        dataStore.setStorage(storage.toModel())
    }
}
