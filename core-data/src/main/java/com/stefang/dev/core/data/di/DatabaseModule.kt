package com.stefang.dev.core.data.di

import android.content.Context
import androidx.room.Room
import com.stefang.dev.core.data.database.AppDatabase
import com.stefang.dev.core.data.database.ArithmeticDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideArithmeticDao(appDatabase: AppDatabase): ArithmeticDao {
        return appDatabase.arithmeticDao()
    }

    companion object {
        private const val DB_NAME = "scan-me-arithmetic-db"
    }
}
