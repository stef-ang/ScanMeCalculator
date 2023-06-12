package com.stefang.core.camera

import com.stefang.image.source.api.ImageSourceApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ImageSourceCameraModule {

    @Singleton
    @Binds
    fun bindsImageSourceCameraApi(
        imageSource: ImageSourceCameraApiImpl
    ): ImageSourceApi

}
