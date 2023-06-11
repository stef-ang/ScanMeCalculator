package com.stefang.image.source.api.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.stefang.image.source.api.ImageSourceApi
import com.stefang.image.source.api.parcelable
import javax.inject.Inject

class ImageSourceCameraApiImpl @Inject constructor() : ImageSourceApi {

    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var intent: Intent

    override fun initLauncher(componentActivity: ComponentActivity, onResult: (Uri) -> Unit) {
        intent = Intent(componentActivity, CameraActivity::class.java)
        launcher = componentActivity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.parcelable<Uri>(ImageSourceApi.EXTRA_PHOTO_URI)
                onResult(uri ?: Uri.EMPTY)
            }
        }
    }

    override fun runLauncher() {
        if (this::launcher.isInitialized && this::intent.isInitialized) {
            launcher.launch(intent)
        } else {
            Throwable("call initLauncher to initiate the launcher first")
        }
    }
}
