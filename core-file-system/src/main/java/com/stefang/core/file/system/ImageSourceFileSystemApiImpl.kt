package com.stefang.core.file.system

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.stefang.image.source.api.ImageSourceApi
import javax.inject.Inject

class ImageSourceFileSystemApiImpl @Inject constructor() : ImageSourceApi {

    private lateinit var launcher: ActivityResultLauncher<PickVisualMediaRequest>

    override fun initLauncher(componentActivity: ComponentActivity, onResult: (Uri) -> Unit) {
        launcher = componentActivity
            .registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                onResult(it ?: Uri.EMPTY)
            }
    }

    override fun runLauncher() {
        if (this::launcher.isInitialized) {
            launcher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            Throwable("call initLauncher to initiate the launcher first")
        }
    }
}
