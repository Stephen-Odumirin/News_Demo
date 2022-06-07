package com.stdev.newsdemo.presentation.di

import com.stdev.newsdemo.domain.repository.NewsRepository
import com.stdev.newsdemo.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideNewsHeadlines(newsRepository: NewsRepository) : GetNewsHeadlineUseCase{
        return GetNewsHeadlineUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesSearchedNewsHeadlines(newsRepository: NewsRepository) : GetSearchedNewsUseCase{
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesSavedNews(newsRepository: NewsRepository) : SaveNewsUseCase{
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesGetSavedNewsUseCase(newsRepository: NewsRepository) : GetSaveNewsUseCase{
        return GetSaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesDeleteNewsUseCase(newsRepository: NewsRepository) : DeleteSaveNewsUseCase{
        return DeleteSaveNewsUseCase(newsRepository)
    }


}
















