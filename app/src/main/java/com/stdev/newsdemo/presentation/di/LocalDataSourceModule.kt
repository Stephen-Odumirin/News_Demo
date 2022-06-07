package com.stdev.newsdemo.presentation.di

import com.stdev.newsdemo.data.db.ArticleDao
import com.stdev.newsdemo.data.repository.datasource.NewsLocalDataSource
import com.stdev.newsdemo.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(articleDao: ArticleDao) : NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDao)
    }
}