package com.stdev.newsdemo.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.stdev.newsdemo.data.model.ApiResponse
import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.data.util.Resource
import com.stdev.newsdemo.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlineUseCase: GetNewsHeadlineUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSaveNewsUseCase: GetSaveNewsUseCase,
    private val deleteSaveNewsUseCase: DeleteSaveNewsUseCase
) : AndroidViewModel(app){

    val newsHeadlines : MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    fun getNewsHeadlines(country : String, page : Int, category : String) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadlines.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)){
                val apiResult = getNewsHeadlineUseCase.execute(country, page, category)
                newsHeadlines.postValue(apiResult)
            }else{
                newsHeadlines.postValue(Resource.Error(message = "Internet not available"))
            }
        }catch (e : Exception){
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }


    }

    private fun isNetworkAvailable(context : Context?) : Boolean{
        if(context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null){
                when{
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }

            }
        }
        return true//todo check this shit
    }

    val searchedNews : MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    fun searchedNewsHeadlines(country: String, category: String, page: Int, searchQuery : String){
        viewModelScope.launch{
            searchedNews.postValue(Resource.Loading())
            try {
                if(isNetworkAvailable(app)){
                    val searchResult = getSearchedNewsUseCase.execute(country, category, page, searchQuery)
                    searchedNews.postValue(searchResult)
                }else{
                    searchedNews.postValue(Resource.Error(message = "Internet not available"))
                }
            }catch (e : Exception){
                searchedNews.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    fun getSavedNews() = liveData {
        getSaveNewsUseCase.execute().collect{
            emit(it)
        }
    }

    fun deleteArticles(article: Article){
        viewModelScope.launch {
            deleteSaveNewsUseCase.execute(article)
        }
    }

}























