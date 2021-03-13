
package com.example.newsshow.logic.model

import com.google.gson.annotations.SerializedName


data class NewsModel(val reason: String,val result: Result) {
    data class Result(val data: List<Data>){
        data class Data(val title: String,val date: String,val url: String,@SerializedName("thumbnail_pic_s") val picture: String)
    }
}