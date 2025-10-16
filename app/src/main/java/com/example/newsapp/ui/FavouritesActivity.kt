package com.example.newsapp.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.BaseActivity
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.api.Article
import com.example.newsapp.databinding.ActivityFavouritesBinding
import com.google.firebase.firestore.FirebaseFirestore

class FavouriteActivity : BaseActivity() {

    private lateinit var binding: ActivityFavouritesBinding
    private lateinit var db: FirebaseFirestore
    private val favArticles = ArrayList<Article>()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.Appbar.root.findViewById<ImageView>(R.id.side_menu).setOnClickListener {
            Toast.makeText(this, "Menu clicked!", Toast.LENGTH_SHORT).show()
            if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                binding.drawer.closeDrawer(GravityCompat.START)
            } else binding.drawer.openDrawer(GravityCompat.START)
        }
        setupNavigation(binding.navigationView, binding.drawer)

        db = FirebaseFirestore.getInstance()
        adapter = NewsAdapter(this, favArticles, true)

        binding.favoritesList.layoutManager = LinearLayoutManager(this)
        binding.favoritesList.adapter = adapter

        binding.progressFavorites.isVisible = true

        loadFavourites()
    }

    private fun loadFavourites() {
        binding.progressFavorites.isVisible = false
        db.collection("favourites")
            .get()
            .addOnSuccessListener { documents ->
                favArticles.clear()
                for (doc in documents) {
                    val article = Article(
                        title = doc.getString("title"),
                        description = doc.getString("description"),
                        urlToImage = doc.getString("urlToImage"),
                        url = doc.getString("url")
                    )
                    favArticles.add(article)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load favourites", Toast.LENGTH_SHORT).show()
            }
    }
}
