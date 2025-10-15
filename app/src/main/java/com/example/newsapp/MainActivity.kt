package com.example.newsapp

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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadNews()

        val menuIcon = binding.root.findViewById<ImageView>(R.id.side_menu)
        menuIcon.setOnClickListener  {
            Log.d("SIDE_MENU", "Clicked!")
            Toast.makeText(this, "Menu clicked!", Toast.LENGTH_SHORT).show()
            if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                binding.drawer.closeDrawer(GravityCompat.START)
            } else binding.drawer.openDrawer(GravityCompat.START)
        }

        binding.navigationView.setNavigationItemSelectedListener(object :
            OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.Logout -> {
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this@MainActivity, SignInActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(this@MainActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()

                        binding.drawer.closeDrawer(GravityCompat.START)
                        return true
                    }
                    R.id.sitting -> {
                        Toast.makeText(this@MainActivity, "not implemented yet", Toast.LENGTH_SHORT)
                            .show()
                        binding.drawer.closeDrawer(GravityCompat.START)
                        return true
                    }
                    R.id.favorites -> {
                        Toast.makeText(this@MainActivity, "not implemented yet", Toast.LENGTH_SHORT)
                            .show()
                        binding.drawer.closeDrawer(GravityCompat.START)
                        return true
                    }
                    else -> return true
                }
            }

        })

        binding.swipeRefresh.setOnRefreshListener {
            loadNews()
        }
    }

    private fun loadNews() {
        val category = intent.getStringExtra("category_name") ?: "general"

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(NewsCallable::class.java)
        newsService.getNewsByCategory(category).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val news = response.body()
                val articles = news?.articles ?: arrayListOf()
                articles.removeAll { it.title == "[Removed]" }

                showNews(articles)
                binding.loader.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                binding.loader.isVisible = false
                Log.e("NewsError", t.message ?: "Unknown error")
            }
        })
    }

    private fun showNews(articles: ArrayList<Article>) {
        val adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter
    }
}
