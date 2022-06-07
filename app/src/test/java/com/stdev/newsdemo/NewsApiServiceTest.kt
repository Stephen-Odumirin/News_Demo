package com.stdev.newsdemo

import com.google.common.truth.Truth.assertThat
import com.stdev.newsdemo.data.api.NewsApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NewsApiServiceTest {

    private lateinit var service : NewsApiService
    private lateinit var server : MockWebServer

    @Before
    fun setUp(){
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responsebody = service.getTopHeadlines("us","business",1).body()
            val request = server.takeRequest()
            assertThat(responsebody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&category=business&page=1&apiKey=fa4d3da839004264be8932512ba045c2")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responsebody = service.getTopHeadlines("us","business",1).body()
            val articleList = responsebody!!.articles
            assertThat(articleList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responsebody = service.getTopHeadlines("us","business",1).body()
            val articleList = responsebody!!.articles
            val article = articleList[0]
            assertThat(article.author).isEqualTo("Ken Martin")
            assertThat(article.url).isEqualTo("https://www.foxbusiness.com/markets/starbucks-closing-ny-cafe-union-calls-retaliation-report")
        }
    }

    @After
    fun tearDown(){
        server.shutdown()
    }

}

















