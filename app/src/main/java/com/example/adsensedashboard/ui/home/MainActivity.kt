package com.example.adsensedashboard.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.ActivityMainBinding
import com.example.adsensedashboard.utils.toast
import com.example.adsensedashboard.viewModels.AuthViewModel
import com.example.adsensedashboard.viewModels.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var mAuth: FirebaseAuth


    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signInButton.setOnClickListener { v -> performSignIn() }
        binding.signOutBtn.setOnClickListener { v -> performSignOut() }


        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(application, activityResultRegistry)
        ).get(AuthViewModel::class.java)

        lifecycle.addObserver(viewModel)
        authenticateWithGoogle()
    }


    private fun authenticateWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = Firebase.auth
    }

    private fun performSignIn() {
        toast("Signing in...")
        if (mAuth.currentUser == null)
            viewModel.signIn(googleSignInClient)
    }

    private fun performSignOut() {
        Firebase.auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            toast("Sign out completed")
        }
    }
}