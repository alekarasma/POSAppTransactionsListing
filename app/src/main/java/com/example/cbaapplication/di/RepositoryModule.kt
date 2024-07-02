package com.example.cbaapplication.di

import android.app.Application
import android.content.Context
import com.example.cbaapplication.data.source.LocalTransactionRepository
import com.example.cbaapplication.domain.IAccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideAccountRepository(context: Context): IAccountRepository {
        return LocalTransactionRepository(context)
    }

}
