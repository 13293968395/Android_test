package com.example.newsshow.logic.network

import com.example.newsshow.logic.model.NewsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("index?key=701e3cbc0f4ba7baa8111edbdd34f416&page_size=50&page=3")
    fun searchNews(@Query("type") type: String): Call<NewsModel>
}