package com.example.adsensedashboard.viewModels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class AuthViewModel(
    app: Application,
    private val registry: ActivityResultRegistry
) : AndroidViewModel(app), DefaultLifecycleObserver {
    private val TAG = "AuthViewModel"
    private lateinit var mAuth: FirebaseAuth
    lateinit var getContent: ActivityResultLauncher<Intent>

    init {
        Log.d(TAG, "AuthViewModel called... ")
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        mAuth = Firebase.auth
        getContent = registry.register(
            "key", owner, ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    fun signIn(googleSignInClient: GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent
        try {
            getContent.launch(signInIntent)
        } catch (e: Exception) {
            Log.d(TAG, "signIn: ${e.message}")
        }
    }

    // [START auth_with_google]
    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    // [END auth_with_google]
    private fun updateUI(user: FirebaseUser?) {
        Log.d(TAG, "updateUI: ${user?.displayName}")
        Log.d(TAG, "updateUI: ${user?.toString()}")

    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser != null)
            updateUI(currentUser)
    }

}