package com.example.adsensedashboard.ui.data

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.adsensedashboard.R
import com.example.adsensedashboard.models.RetrofitClient
import com.example.adsensedashboard.utils.EMAIL
import com.example.adsensedashboard.utils.SOURCE
import com.example.adsensedashboard.utils.USER
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : AppCompatActivity() {
    private val TAG = "DashboardActivity"
    private var mAuth: FirebaseAuth = Firebase.auth

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val source = intent.getStringExtra(SOURCE)
        val userName = intent.getStringExtra(USER)
        val email = intent.getStringExtra(EMAIL)

        Log.d(TAG, "updateUI: ${source}")
        Log.d(TAG, "updateUI: ${userName}")
        Log.d(TAG, "updateUI: ${email}")


    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            val user = mAuth.currentUser
            val accountName: String? = user?.displayName
            Log.d(TAG, "onCreate: Account Name : $accountName ")

            try {
                val uid = mAuth.uid ?: return@launch
                val accountId =
                    GoogleSignIn.getLastSignedInAccount(this@DashboardActivity)?.let { it.id }
                        ?: return@launch
                //                val accountId =
//                    GoogleAuthUtil.getAccountId(this@DashboardActivity, accountName ?: "")
                Log.d(TAG, "onCreate: Account Id : $accountId ")
                Log.d(TAG, "onCreate: Account Id : ${uid} ")
                val adClients = getAdClients(uid)
                Log.d(TAG, "onStart: Ad Client ID: ${adClients}")
                adClients?.let {


//                    for (adClient in it) {
//                        // Process each Ad Client
//                        Log.d(TAG, "onStart: Ad Client ID: ${adClient.id}")
//                    }
                }
            } catch (e: Exception) {
                TODO("Not yet implemented")
                Log.e(TAG, "onStart: ERROR ========> ${e.message}")
            }

        }
    }

    suspend fun getAdClients(accountId: String) = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.adSenseService.getAdClients(accountId)
            response?.let { res ->
//                Log.d(TAG, "getAdClients: ${res.adClients}")
//                res.adClients
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}