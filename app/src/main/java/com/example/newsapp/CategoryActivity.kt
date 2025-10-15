package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.newsapp.adapters.CategoriesAdapter
import com.example.newsapp.databinding.ActivityCategoryBinding
import com.example.newsapp.model.CategoryModel
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth

class CategoryActivity() : AppCompatActivity() {
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

        binding.navigationView.setNavigationItemSelectedListener(object :
            OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.Logout -> {
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this@CategoryActivity, SignInActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(this@CategoryActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()

                        binding.drawer.closeDrawer(GravityCompat.START)
                        return true
                    }
                    R.id.sitting -> {
                        Toast.makeText(this@CategoryActivity, "not implemented yet", Toast.LENGTH_SHORT)
                            .show()
                        binding.drawer.closeDrawer(GravityCompat.START)
                        return true
                    }
                    R.id.favorites -> {
                        Toast.makeText(this@CategoryActivity, "not implemented yet", Toast.LENGTH_SHORT)
                            .show()
                        binding.drawer.closeDrawer(GravityCompat.START)
                        return true
                    }
                    else -> return true
                }
            }

        })
    }
}