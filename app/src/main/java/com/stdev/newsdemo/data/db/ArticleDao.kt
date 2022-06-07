package com.stdev.newsdemo.data.db

import androidx.room.*
import com.stdev.newsdemo.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("select * from articles")
    fun getAllArticles() : Flow<List<Article>>

    @Delete
    suspend fun delete(article: Article)

}