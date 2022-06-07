package com.stdev.newsdemo.data.repository.datasourceImpl

import com.stdev.newsdemo.data.db.ArticleDao
import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val articleDao: ArticleDao) : NewsLocalDataSource {
    override suspend fun saveArticleToDb(article: Article) {
        articleDao.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDao.getAllArticles()
    }

    override suspend fun deleteArticlesFromDb(article: Article) {
        articleDao.delete(article)
    }
}