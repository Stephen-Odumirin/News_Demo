package com.stdev.newsdemo.presentation.di

import android.app.Application
import com.stdev.newsdemo.domain.usecase.*
import com.stdev.newsdemo.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(application: Application,getNewsHeadlineUseCase: GetNewsHeadlineUseCase,getSearchedNewsUseCase: GetSearchedNewsUseCase,saveNewsUseCase: SaveNewsUseCase,getSaveNewsUseCase: GetSaveNewsUseCase,deleteSaveNewsUseCase: DeleteSaveNewsUseCase) : NewsViewModelFactory{
        return NewsViewModelFactory(application,getNewsHeadlineUseCase,getSearchedNewsUseCase,saveNewsUseCase,getSaveNewsUseCase,deleteSaveNewsUseCase)
    }


}