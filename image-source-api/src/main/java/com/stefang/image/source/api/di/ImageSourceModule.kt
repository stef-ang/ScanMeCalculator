package com.stefang.image.source.api.di

import com.stefang.image.source.api.ImageSourceApi
import com.stefang.image.source.api.camera.ImageSourceCameraApiImpl
import com.stefang.image.source.api.filesystem.ImageSourceFileSystemApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ImageSourceModule {

    @Singleton
    @Binds
    fun bindsImageSourceCameraApi(
        imageSource: ImageSourceCameraApiImpl
    ): ImageSourceApi

//    @Singleton
//    @Binds
//    fun bindsImageSourceFileSystemApi(
//        imageSource: ImageSourceFileSystemApiImpl
//    ): ImageSourceApi
}
