package com.stdev.newsdemo.presentation.di

import com.stdev.newsdemo.data.api.NewsApiService
import com.stdev.newsdemo.data.repository.datasource.NewsRemoteDataSource
import com.stdev.newsdemo.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsApiService: NewsApiService) : NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsApiService)
    }

}