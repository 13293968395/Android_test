package com.example.newsshow.logic

import androidx.lifecycle.liveData
import com.example.newsshow.logic.network.NewsNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchNews(type: String) = fire(Dispatchers.IO) {
        val newsResponse = NewsNetwork.searchNews(type)
        if (newsResponse.reason == "success!"||newsResponse.reason == "success") {
            val newsList = newsResponse.result.data
            Result.success(newsList)
        }else {
            Result.failure(RuntimeException("response reason is ${newsResponse.reason}"))
        }
    }



    private fun <T> fire(context: CoroutineContext,block: suspend () -> Result<T>) = liveData<Result<T>>(context) {
        val result = try {
            block()
        }catch (e:Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}