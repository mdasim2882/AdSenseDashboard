package com.example.adsensedashboard.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.example.adsensedashboard.models.ResponseAdSenseAPI
import com.example.adsensedashboard.models.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DashboardViewModel(
    app: Application
) : AndroidViewModel(app), DefaultLifecycleObserver {

    private val TAG = "DashboardViewModel"
    var response = MutableLiveData<ResponseAdSenseAPI?>(null)

    suspend fun getAccount(accessToken: String, accept: String) =
        withContext(Dispatchers.IO) {
            try {
                response.postValue(
                    RetrofitClient.adSenseService
                        .getAccounts(accessToken, accept).body()
                )

                Log.d(TAG, "getAccounts: RESPONSE RETROFIT= ${response.value}..")
                response.value?.let { res ->
                    Log.d(TAG, "getAccount: DATA FROM CLASS ${res.accounts[0]}")
                    Log.d(TAG, "getAccount: DATA : account_name = ${res.accounts[0].name}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                updateUI()
                null
            }
        }


    private fun updateUI() {
        // TODO: Update UI in case of Retrofit API call failure
    }

}