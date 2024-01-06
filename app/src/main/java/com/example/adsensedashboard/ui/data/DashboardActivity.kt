package com.example.adsensedashboard.ui.data

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.ActivityDashboardBinding
import com.example.adsensedashboard.models.ResponseAdSenseAPI
import com.example.adsensedashboard.utils.toast
import com.example.adsensedashboard.viewModels.DashboardViewModel
import com.example.adsensedashboard.viewModels.DashboardViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class DashboardActivity : AppCompatActivity() {
    private val TAG = "DashboardActivity"
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewModel: DashboardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupArchitectureComponents()
        setOnClickButtons()
        makeAPICall()
    }

    private fun setOnClickButtons() {
        // TODO("Not yet implemented")
        binding.logoutBtn.setOnClickListener { v: View -> performSignOut() }
    }

    private fun performSignOut() {
        Firebase.auth.signOut()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            toast("SignOut Error...")
            return
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestScopes(Scope("https://www.googleapis.com/auth/adsense.readonly"))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut().addOnCompleteListener {
            toast("Sign out completed")
            finish()
        }
    }

    private fun setupArchitectureComponents() {
        viewModel = ViewModelProvider(
            this,
            DashboardViewModelFactory(application)
        ).get(DashboardViewModel::class.java)

        lifecycle.addObserver(viewModel)
        viewModel.response.observe(this) {
            Log.d(TAG, "onCreate: Fetching Response...from Observer")
            if (it == null) {
                Log.d(TAG, "onCreate: NULL Response")
                return@observe
            }
            updateUI(it)
        }
    }

    private fun updateUI(responseAdSenseAPI: ResponseAdSenseAPI) {
        val JSON_DATA = Gson().toJson(responseAdSenseAPI)
        binding.sampleApiRespnse.text = JSON_DATA
        Log.d(TAG, "updateUI: RESPONSE ON DASHBOARD UI= $JSON_DATA")
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
            val token = generateAccessToken()
            Log.d(TAG, "makeAPICall: ACCESS TOKEN : ${token}")
            token?.let { viewModel.getAccount("Bearer ${token}", "application/json") }
        }
    }

    private fun generateAccessToken(): String? {
        var token: String? = null
        try {
            val currentAccount = GoogleSignIn.getLastSignedInAccount(this@DashboardActivity)
            currentAccount?.let { signin ->
                val credential = GoogleAccountCredential.usingOAuth2(
                    this@DashboardActivity,
                    listOf("https://www.googleapis.com/auth/adsense.readonly")
                ).setSelectedAccount(signin.account)
                // TODO : Setup Backoff if needed

                token = credential.token
            }
        } catch (e: Exception) {
            TODO("Handle the AdSenseAPI Retrofit response exception")
        }
        return token
    }


}