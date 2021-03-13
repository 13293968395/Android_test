package com.example.newsshow.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.newsshow.logic.Repository
import com.example.newsshow.logic.model.NewsModel

class NewsViewModel: ViewModel() {

    private val searchNewsLiveData = MutableLiveData<String>()

    val newsList = ArrayList<NewsModel.Result.Data>()

    val newsLiveData = Transformations.switchMap(searchNewsLiveData) { type->
        Repository.searchNews(type)
    }

    fun searchNews(type: String) {
        searchNewsLiveData.value = type
    }

}