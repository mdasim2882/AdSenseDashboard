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
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

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

        val init = FirebaseApp.initializeApp(this)
        Log.d(TAG, "onCreate: INIT FIREBASE APP $init")
        //region OKHttpClient API Call
        GlobalScope.launch {
            val user = mAuth.currentUser
            val accountName: String? = user?.displayName
            Log.d(TAG, "onCreate: Account Name : $accountName ")

            val currentAccount = GoogleSignIn.getLastSignedInAccount(this@DashboardActivity)
            currentAccount?.let {
                val credential = GoogleAccountCredential.usingOAuth2(
                    this@DashboardActivity,
                    listOf("https://www.googleapis.com/auth/adsense.readonly")
                )
                credential.setSelectedAccount(it.account)
                val token = credential.token
                Log.d(TAG + "OkHttp", "makeAPICall: ACCESS TOKEN : $token")
                try {
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("https://adsense.googleapis.com/v2/accounts")
                        .addHeader("Authorization", "Bearer ${token}")
                        .addHeader("Accept", "application/json")
                        .get()
                        .build()

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            TODO("Not yet implemented")
                            e.printStackTrace()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            Log.d(
                                TAG + "OkHttp",
                                "onResponse: ==========> ${
                                    response.body()?.string()
                                } \n\n ${
                                    response.body().toString()
                                }"
                            )
                            Log.d(
                                TAG + "OkHttp",
                                "onResponse: ==========> ${response.networkResponse()} \n\n CODE = ${response.code()}"
                            )
                            Log.d(
                                TAG + "OkHttp",
                                "onResponse: ==========> STATUS = ${response.isSuccessful} \n\n MESSAGE = ${response.message()}"
                            )
                        }

                    })
                } catch (e: Exception) {
                    TODO("Handle OkHttpException here ")
                }
            }
        }
        //endregion
    }


    //    @OptIn(DelicateCoroutinesApi::class)
    override fun onStart() {
        super.onStart()

//        OkHttpClient client = OkHttpClient
        //region RETROFIT API CALL
        makeAPICall()
        //endregion
    }

    private fun makeAPICall() {
        GlobalScope.launch {
            val user = mAuth.currentUser
            val accountName: String? = user?.displayName
            Log.d(TAG, "onCreate: Account Name : $accountName ")

            val currentAccount = GoogleSignIn.getLastSignedInAccount(this@DashboardActivity)
            currentAccount?.let {
                val credential = GoogleAccountCredential.usingOAuth2(
                    this@DashboardActivity,
                    listOf("https://www.googleapis.com/auth/adsense.readonly")
                )
                credential.setSelectedAccount(it.account)
                val token = credential.token
                Log.d(TAG, "makeAPICall: ACCESS TOKEN : $token")
                try {

                    val adClients = getAccount(
                        "Bearer $token",
                        "application/json"
                    )
                    Log.d(TAG, "onStart: Ad Client ID: ${adClients}")
                } catch (e: Exception) {
                    TODO("Handle the response exception")
                }
            }
        }
    }

    suspend fun getAccount(
        accessToken: String,
        accept: String
    ) =
        withContext(Dispatchers.IO) {
            try {
                val response =
                    RetrofitClient.adSenseService.getAccounts(
                        accessToken,
                        accept
                    )
                Log.d(TAG, "getAccounts: RESPONSE = ${response}..")
                Log.d(TAG, "getAccounts: RESPONSE BODY RETROFIT = ${response.asJsonObject}..")
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