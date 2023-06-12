package com.stefang.dev.feature.calculator.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.text.Text
import com.stefang.dev.core.data.ArithmeticHistoryRepository
import com.stefang.dev.feature.calculator.ArithmeticData
import com.stefang.dev.feature.calculator.ArithmeticExtractorUseCase
import com.stefang.dev.feature.calculator.mapper.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagePreviewViewModel @Inject constructor(
    private val extractor: ArithmeticExtractorUseCase,
    private val repository: ArithmeticHistoryRepository
) : ViewModel() {

    private val inputTextFlow = MutableStateFlow("-")
    val inputTextState: StateFlow<String> = inputTextFlow

    private val resultTextFlow = MutableStateFlow("-")
    val resultTextState: StateFlow<String> = resultTextFlow

    fun updateVisionText(visionText: Text, uri: Uri) {
        visionText.textBlocks.forEach {
            val arithmeticData = extractor.extract(it.text)
            if (arithmeticData != null) {
                inputTextFlow.value = arithmeticData.getInput()
                resultTextFlow.value = arithmeticData.getResult()
                recordResult(arithmeticData, uri)
                return
            }
        }
    }

    private fun recordResult(data: ArithmeticData, uri: Uri) {
        viewModelScope.launch {
            repository.recordHistory(
                data.toModel(uri),
                System.currentTimeMillis()
            )
        }
    }
}
