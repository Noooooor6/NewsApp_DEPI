package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.newsapp.BaseActivity
import com.example.newsapp.R
import com.example.newsapp.adapters.CategoriesAdapter
import com.example.newsapp.databinding.ActivityCategoryBinding
import com.example.newsapp.model.CategoryModel
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth

class CategoryActivity() : BaseActivity() {
    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoriesAdapter = CategoriesAdapter(CategoryModel.categoryModels){ category ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("category_name", category.title.lowercase())
            startActivity(intent)
        }
        binding.categoryRV.adapter = categoriesAdapter

        binding.Appbar.root.findViewById<ImageView>(R.id.side_menu).setOnClickListener {
            Toast.makeText(this, "Menu clicked!", Toast.LENGTH_SHORT).show()
            if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                binding.drawer.closeDrawer(GravityCompat.START)
            } else binding.drawer.openDrawer(GravityCompat.START)
        }

        setupNavigation(binding.navigationView, binding.drawer)

    }
}