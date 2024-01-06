package com.example.adsensedashboard.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.ActivityMainBinding
import com.example.adsensedashboard.ui.data.ConsoleActivity
import com.example.adsensedashboard.utils.toast
import com.example.adsensedashboard.viewModels.AuthViewModel
import com.example.adsensedashboard.viewModels.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        updateUI()
        setupArchitectureComponents()
        authenticateWithGoogle()
    }

    //region Helper Methods
    private fun updateUI() {
        binding.signInButton.setOnClickListener { v -> performSignIn() }
        binding.signOutBtn.setOnClickListener { v -> performSignOut() }
    }

    private fun setupArchitectureComponents() {
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(application, activityResultRegistry)
        ).get(AuthViewModel::class.java)

        lifecycle.addObserver(viewModel)
        viewModel.userLiveData.observe(this) { user ->
            Log.d(TAG, "onCreate: Calling ConsoleActivity...from Observer")
            if (user == null) {
                Log.d(TAG, "onCreate: NULL User Data")
                return@observe
            }
            Intent(this, ConsoleActivity::class.java)
                .apply { startActivity(this) }

        }
    }


    private fun authenticateWithGoogle() {
        mAuth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestScopes(Scope("https://www.googleapis.com/auth/adsense.readonly"))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    //endregion

    //region OnClick Methods
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
    //endregion
}