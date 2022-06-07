package com.stdev.newsdemo.presentation.di

import com.stdev.newsdemo.data.repository.NewsRepositoryImpl
import com.stdev.newsdemo.data.repository.datasource.NewsLocalDataSource
import com.stdev.newsdemo.data.repository.datasource.NewsRemoteDataSource
import com.stdev.newsdemo.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesNewsRepository(newsRemoteDataSource: NewsRemoteDataSource,newsLocalDataSource: NewsLocalDataSource) : NewsRepository{
        return NewsRepositoryImpl(newsRemoteDataSource,newsLocalDataSource)
    }
}