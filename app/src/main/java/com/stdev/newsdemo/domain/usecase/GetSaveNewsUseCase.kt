package com.stdev.newsdemo.domain.usecase

import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow


class GetSaveNewsUseCase(private val repository: NewsRepository) {

    fun execute() : Flow<List<Article>> {
        return repository.getSavedNews()
    }

}