package com.stefang.image.source.api

import android.content.Context
import android.content.Intent

interface ImageSourceApi {

    fun getAccessImageIntent(packageContext: Context): Intent

    companion object {
        const val EXTRA_PHOTO_URI = "extra_photo_uri"
    }
}
