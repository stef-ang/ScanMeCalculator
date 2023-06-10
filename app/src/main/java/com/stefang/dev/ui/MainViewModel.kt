package com.stefang.dev.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val uriImageSharedFlow = MutableSharedFlow<Uri>()
    val uriImageFlow: SharedFlow<Uri> = uriImageSharedFlow

    fun setUriImage(uri: Uri) = viewModelScope.launch {
        uriImageSharedFlow.emit(uri)
    }
}
