package com.stdev.newsdemo.domain.repository

import androidx.lifecycle.LiveData
import com.stdev.newsdemo.data.model.ApiResponse
import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(country : String, page : Int, category : String) : Resource<ApiResponse>
    suspend fun getSearchedNews(country: String,page: Int,category: String,searchQuery : String) : Resource<ApiResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews() : Flow<List<Article>>

}