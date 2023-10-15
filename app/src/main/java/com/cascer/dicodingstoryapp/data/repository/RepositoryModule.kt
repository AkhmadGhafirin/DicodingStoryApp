package com.cascer.dicodingstoryapp.data.repository

import com.cascer.dicodingstoryapp.data.api.ApiService
import com.cascer.dicodingstoryapp.utils.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideRepositoryImpl(
        appPreferences: AppPreferences,
        retrofit: Retrofit
    ): Repository {
        return RepositoryImpl(appPreferences, retrofit.create(ApiService::class.java))
    }
}