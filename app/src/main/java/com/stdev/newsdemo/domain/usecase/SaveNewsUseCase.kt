package com.stdev.newsdemo.domain.usecase

import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.domain.repository.NewsRepository

class SaveNewsUseCase(private val repository: NewsRepository) {

    suspend fun execute(article: Article){
        repository.saveNews(article)
    }

}