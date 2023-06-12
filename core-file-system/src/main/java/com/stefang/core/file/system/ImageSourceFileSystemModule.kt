package com.stefang.core.file.system

import com.stefang.image.source.api.ImageSourceApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ImageSourceFileSystemModule {

    @Singleton
    @Binds
    fun bindsImageSourceFileSystemApi(
        imageSource: ImageSourceFileSystemApiImpl
    ): ImageSourceApi
}
