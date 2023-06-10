package com.stefang.dev.feature.calculator.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.text.Text
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ImagePreviewViewModel @Inject constructor() : ViewModel() {
    private val TAG = "ImagePreviewVM"

    private val visionTextFlow = MutableStateFlow("")
    val visionTextState: StateFlow<String> = visionTextFlow

    fun updateVisionText(visionText: Text) {
        Log.d(TAG, "updateVisionText: ${visionText.text}")
        visionTextFlow.value = visionText.text
    }
}
