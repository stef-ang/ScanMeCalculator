package com.stefang.dev.feature.calculator.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.text.Text
import com.stefang.dev.feature.calculator.ArithmeticExtractorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ImagePreviewViewModel @Inject constructor(
    private val extractor: ArithmeticExtractorUseCase
) : ViewModel() {
    private val TAG = "ImagePreviewVM"

    private val inputTextFlow = MutableStateFlow("-")
    val inputTextState: StateFlow<String> = inputTextFlow

    private val resultTextFlow = MutableStateFlow("-")
    val resultTextState: StateFlow<String> = resultTextFlow

    fun updateVisionText(visionText: Text) {
        Log.d(TAG, "updateVisionText: ${visionText.text}")
        visionText.textBlocks.forEach {
            val arithmeticData = extractor.extract(it.text)
            if (arithmeticData != null) {
                inputTextFlow.value = arithmeticData.getInput()
                resultTextFlow.value = arithmeticData.getResult()
                return@forEach
            }
        }
    }
}
