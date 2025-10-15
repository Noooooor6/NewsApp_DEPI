package com.example.newsapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {

    @GET("v2/top-headlines")
    fun getNewsByCategory(
        @Query("category") category: String,
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = "96d7169f9d0a42cab90d303c3d289906"
    ): Call<NewsResponse>
   }