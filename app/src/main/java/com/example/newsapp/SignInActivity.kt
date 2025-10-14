package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.newsapp.databinding.ActivitySignInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.SignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.SignInBtn.setOnClickListener {
            val email = binding.EmailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Missing fields", Toast.LENGTH_SHORT).show()
            } else
                binding.loader.isVisible = true
            sinIn(email, password)

        }

        binding.forgotPassTv.setOnClickListener {
            binding.loader.isVisible = true
            val emailAddress = binding.EmailEdt.text.toString()

            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.loader.isVisible = false
                        Toast.makeText(this, "Email Sent!", Toast.LENGTH_SHORT).show();
                    }
                }

        }

    }

    private fun sinIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.loader.isVisible = false
                    if (auth.currentUser!!.isEmailVerified) {
                        startActivity(Intent(this, CategoryActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Check Your Email!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show();
                    binding.loader.isVisible = false
                }

            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            startActivity(Intent(this, CategoryActivity::class.java))
            finish()
        }
    }
}

