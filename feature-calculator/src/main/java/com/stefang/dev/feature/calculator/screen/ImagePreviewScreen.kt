package com.stefang.dev.feature.calculator.screen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.stefang.dev.feature.calculator.viewmodel.ImagePreviewViewModel
import java.io.IOException

private const val TAG = "ImagePreviewScreen"

@Composable
fun ImagePreviewScreen(
    photoUri: Uri?,
    context: Context = LocalContext.current,
    viewModel: ImagePreviewViewModel = hiltViewModel()
) {
    val inputText by viewModel.inputTextState.collectAsStateWithLifecycle()
    val resultText by viewModel.resultTextState.collectAsStateWithLifecycle()

    lateinit var detector: TextRecognizer
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        detector = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        lifecycle.addObserver(detector)

        photoUri?.let { uri ->
            val image: InputImage
            try {
                image = InputImage.fromFilePath(context, uri)
                detector.process(image)
                    .addOnSuccessListener { visionText ->
                        viewModel.updateVisionText(visionText, uri)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Text recognition error", e)
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        onDispose {
            lifecycle.removeObserver(detector)
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        inputText.takeIf { it.isNotEmpty() }?.let {
            Text(
                text = "Input: $it",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        resultText.takeIf { it.isNotEmpty() }?.let {
            Text(
                text = "Result: $it",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        photoUri?.let { uri ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uri)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}


