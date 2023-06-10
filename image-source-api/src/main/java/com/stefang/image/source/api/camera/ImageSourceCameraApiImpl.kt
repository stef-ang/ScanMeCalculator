package com.stefang.image.source.api.camera

import android.content.Context
import android.content.Intent
import com.stefang.image.source.api.ImageSourceApi
import javax.inject.Inject

class ImageSourceCameraApiImpl @Inject constructor() : ImageSourceApi {
    override fun getAccessImageIntent(packageContext: Context): Intent {
        return Intent(packageContext, CameraActivity::class.java)
    }
}
