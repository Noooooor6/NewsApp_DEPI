package com.example.newsapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import com.example.newsapp.BaseActivity
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.api.Article
import com.example.newsapp.api.NewsCallable
import com.example.newsapp.api.NewsResponse
import com.example.newsapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadNews()

        val menuIcon = binding.root.findViewById<ImageView>(R.id.side_menu)
        menuIcon.setOnClickListener  {
            if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                binding.drawer.closeDrawer(GravityCompat.START)
            } else binding.drawer.openDrawer(GravityCompat.START)
        }

        setupNavigation(binding.navigationView, binding.drawer)

        binding.swipeRefresh.setOnRefreshListener {
            loadNews()
        }
    }

    private fun loadNews() {
        showLoader(true)
        val prefs = getSharedPreferences("NewsPrefs", Context.MODE_PRIVATE)
        val selectedCountry = prefs.getString("selected_country", "Egypt") ?: "Egypt"

        val countryCode = when (selectedCountry) {
            "Egypt" -> "eg"
            "Germany" -> "de"
            "United State" -> "us"
            else -> "us"
        }
        val category = intent.getStringExtra("category_name") ?: "general"

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(NewsCallable::class.java)
        newsService.getNewsByCategory(category, countryCode).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val news = response.body()
                val articles = news?.articles ?: arrayListOf()
                articles.removeAll { it.title == "[Removed]" }

                showNews(articles)
                showLoader(false)
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                binding.loader.isVisible = false
                Log.e("NewsError", t.message ?: "Unknown error")
            }
        })
    }

    private fun showLoader(t: Boolean){
        binding.loader.isVisible = t
    }
    private fun showNews(articles: ArrayList<Article>) {
        val adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter
    }
}
