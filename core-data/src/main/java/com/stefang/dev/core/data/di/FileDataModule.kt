package com.stefang.dev.core.data.di

import android.content.Context
import com.google.gson.Gson
import com.stefang.dev.core.data.file.CryptoFile
import com.stefang.dev.core.data.file.CryptoFileKeyStore
import com.stefang.dev.core.data.file.FileDataSource
import com.stefang.dev.core.data.file.FileDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FileDataModule {

    companion object {

        @Provides
        @Singleton
        fun provideFileDataSourceImpl(
            cryptoFile: CryptoFile,
            @ApplicationContext appContext: Context
        ): FileDataSource {
            return FileDataSourceImpl(cryptoFile, appContext, Gson())
        }

        @Provides
        @Singleton
        fun provideCryptoFileKeyStore(): CryptoFile {
            return CryptoFileKeyStore(Gson())
        }
    }
}
