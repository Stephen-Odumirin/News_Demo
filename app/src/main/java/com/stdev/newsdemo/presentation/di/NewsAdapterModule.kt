package com.stdev.newsdemo.presentation.di

import com.stdev.newsdemo.presentation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsAdapterModule {
    @Singleton
    @Provides
    fun provideNewsAdapter() : NewsAdapter{
        return NewsAdapter()
    }

}