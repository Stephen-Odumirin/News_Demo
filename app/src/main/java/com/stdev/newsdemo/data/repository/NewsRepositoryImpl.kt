package com.stdev.newsdemo.data.repository

import com.stdev.newsdemo.data.model.ApiResponse
import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.data.repository.datasource.NewsLocalDataSource
import com.stdev.newsdemo.data.repository.datasource.NewsRemoteDataSource
import com.stdev.newsdemo.data.util.Resource
import com.stdev.newsdemo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {
    override suspend fun getNewsHeadlines(country : String, page : Int, category : String): Resource<ApiResponse> {
        return responseToResult(remoteDataSource.getTopHeadlines(country, page, category))
    }

    override suspend fun getSearchedNews(country: String, page: Int, category: String, searchQuery: String): Resource<ApiResponse> {
        return responseToResult(remoteDataSource.getSearchedNews(country, page, searchQuery, category))
    }

    private fun responseToResult(response: Response<ApiResponse>) : Resource<ApiResponse>{
        if (response.isSuccessful){
            response.body()?.let { result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }


    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDb(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticlesFromDb(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }
}
















