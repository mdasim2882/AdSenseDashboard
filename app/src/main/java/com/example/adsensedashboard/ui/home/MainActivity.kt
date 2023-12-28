package com.example.adsensedashboard.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.ActivityMainBinding
import com.example.adsensedashboard.ui.auth.GoogleSignInActivity
import com.example.adsensedashboard.utils.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signInButton.setOnClickListener { v -> performSignIn() }

    }

    private fun performSignIn() {
        toast("Signing in...")
        Intent(this, GoogleSignInActivity::class.java).apply {
            startActivity(this)
        }
    }
}