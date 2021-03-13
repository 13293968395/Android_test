package com.example.newsshow.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsServiceCreator {

    private const val BASE_URL = "http://v.juhe.cn/toutiao/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create() :T = create(T::class.java)

}