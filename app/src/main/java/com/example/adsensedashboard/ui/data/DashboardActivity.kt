package com.example.adsensedashboard.ui.data

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.adsensedashboard.R
import com.example.adsensedashboard.models.RetrofitClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        makeAPICall()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callAPIusingOkHttpClient() {
        GlobalScope.launch {

            val currentAccount = GoogleSignIn.getLastSignedInAccount(this@DashboardActivity)
            currentAccount?.let { signin ->
                val CREDENTIAL = GoogleAccountCredential.usingOAuth2(
                    this@DashboardActivity,
                    listOf("https://www.googleapis.com/auth/adsense.readonly")
                ).setSelectedAccount(signin.account)

                val TOKEN = CREDENTIAL.token
                Log.d(TAG, "OkHttp makeAPICall: ACCESS TOKEN : $TOKEN")
                try {
                    val CLIENT = OkHttpClient()
                    val REQUEST = Request
                        .Builder()
                        .url("https://adsense.googleapis.com/v2/accounts")
                        .addHeader("Authorization", "Bearer ${TOKEN}")
                        .addHeader("Accept", "application/json").get().build()

                    //region HANDLING OkHttpClient Response
                    CLIENT.newCall(REQUEST).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            TODO("onFailure() OkHttpClient not yet implemented")
                            e.printStackTrace()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            Log.d(
                                TAG,
                                "OkHttp onResponse : RESPONSE = ${response.body()?.string()}"
                            )
                        }
                    })
                    //endregion

                } catch (e: Exception) {
                    TODO("Handle OkHttpException here ")
                }
            }
        }
    }


    private fun makeAPICall() {
        GlobalScope.launch {
            try {
                val currentAccount = GoogleSignIn.getLastSignedInAccount(this@DashboardActivity)
                currentAccount?.let { signin ->
                    val credential = GoogleAccountCredential.usingOAuth2(
                        this@DashboardActivity,
                        listOf("https://www.googleapis.com/auth/adsense.readonly")
                    ).setSelectedAccount(signin.account)
                    // TODO : Setup Backoff if needed

                    Log.d(TAG, "makeAPICall: ACCESS TOKEN : ${credential.token}")
                    getAccount("Bearer ${credential.token}", "application/json")
                }
            } catch (e: Exception) {
                TODO("Handle the AdSenseAPI Retrofit response exception")
            }
        }
    }

    private suspend fun getAccount(accessToken: String, accept: String) =
        withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.adSenseService
                    .getAccounts(accessToken, accept)

                Log.d(TAG, "getAccounts: RESPONSE RETROFIT= ${response}..")
                response.body()?.let { res ->
                    Log.d(TAG, "getAccount: DATA FROM CLASS ${res.accounts[0]}")
                    Log.d(TAG, "getAccount: DATA : account_name = ${res.accounts[0].name}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
}