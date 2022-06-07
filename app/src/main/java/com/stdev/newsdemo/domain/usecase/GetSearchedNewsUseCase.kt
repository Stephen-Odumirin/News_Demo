package com.stdev.newsdemo.domain.usecase

import com.stdev.newsdemo.data.model.ApiResponse
import com.stdev.newsdemo.data.util.Resource
import com.stdev.newsdemo.domain.repository.NewsRepository


class GetSearchedNewsUseCase(private val repository: NewsRepository) {

    suspend fun execute(country:String,category : String,page : Int,searchQuery : String) : Resource<ApiResponse>{
        return repository.getSearchedNews(country,page,category,searchQuery)
    }

}