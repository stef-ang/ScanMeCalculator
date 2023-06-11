package com.stefang.image.source.api

import android.net.Uri
import androidx.activity.ComponentActivity

interface ImageSourceApi {

    fun initLauncher(
        componentActivity: ComponentActivity,
        onResult: (Uri) -> Unit
    )

    fun runLauncher()

    companion object {
        const val EXTRA_PHOTO_URI = "extra_photo_uri"
    }
}
