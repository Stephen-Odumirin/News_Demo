package com.stdev.newsdemo.data.repository.datasource

import com.stdev.newsdemo.data.model.ApiResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country : String, page : Int, category : String) : Response<ApiResponse>
    suspend fun getSearchedNews(country : String, page : Int, searchQuery : String, category : String) : Response<ApiResponse>
}