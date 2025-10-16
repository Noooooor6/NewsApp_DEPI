package com.example.newsapp

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.newsapp.ui.CategoryActivity
import com.example.newsapp.ui.FavouriteActivity
import com.example.newsapp.ui.SettingsActivity
import com.example.newsapp.ui.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.navigation.NavigationView

open class BaseActivity : AppCompatActivity() {


    protected fun setupNavigation(navigationView: NavigationView, drawerLayout: androidx.drawerlayout.widget.DrawerLayout) {

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.sitting -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.category -> {
                    startActivity(Intent(this, CategoryActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.favorites -> {
                    startActivity(Intent(this, FavouriteActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> true
            }
        }
    }
}
