package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.newsapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signInTv.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        binding.SignUpBtn.setOnClickListener {
            val email = binding.EmailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Missing fields", Toast.LENGTH_SHORT).show()
            } else
                binding.loader.isVisible = true
            signUp(email, password)

        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    verifyEmail()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show();
                    binding.loader.isVisible = false
                }
            }
    }

    private fun verifyEmail() {
        val user = Firebase.auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "User added!", Toast.LENGTH_SHORT).show();
                    startActivity(Intent(this, SignInActivity::class.java))
                    finish()
                }
            }
    }

}

