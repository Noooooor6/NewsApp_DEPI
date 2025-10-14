package com.example.newsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.adapters.CategoriesAdapter
import com.example.newsapp.databinding.ActivityCategoryBinding
import com.example.newsapp.model.CategoryModel

class CategoryActivity : AppCompatActivity() {
    var categoriesAdapter = CategoriesAdapter(CategoryModel.categoryModels)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.categoryRV.adapter = categoriesAdapter
    }
}