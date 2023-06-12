package com.stefang.dev.feature.calculator.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefang.dev.core.data.ArithmeticHistoryRepository
import com.stefang.dev.feature.calculator.ArithmeticData
import com.stefang.dev.feature.calculator.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: ArithmeticHistoryRepository
): ViewModel() {

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
}
