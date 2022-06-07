package com.stdev.newsdemo.domain.usecase

import com.stdev.newsdemo.data.model.ApiResponse
import com.stdev.newsdemo.data.util.Resource
import com.stdev.newsdemo.domain.repository.NewsRepository

class GetNewsHeadlineUseCase(private val repository: NewsRepository) {

    suspend fun execute(country : String, page : Int, category : String) : Resource<ApiResponse>{
        return repository.getNewsHeadlines(country, page, category)
    }

}