package com.example.newsapp.api

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: ArrayList<Article>?
)

data class Article(
    val source: Source? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)

data class Source(
    val id: String?,
    val name: String?
)