package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {

    @GET("/v2/top-headlines?country=us&category=general&apiKey=96d7169f9d0a42cab90d303c3d289906&pageSize=30")
    fun getNews(): Call<News>
}