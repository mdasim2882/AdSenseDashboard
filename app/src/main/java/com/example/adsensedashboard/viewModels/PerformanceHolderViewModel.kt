package com.example.adsensedashboard.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.models.RetrofitClient
import com.example.adsensedashboard.models.api.PerformanceTest
import com.example.adsensedashboard.models.api.ResponseAdSenseAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PerformanceHolderViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PerformanceHolderViewModel(app) as T
    }
}


class PerformanceHolderViewModel(app: Application) : AndroidViewModel(app),
    DefaultLifecycleObserver {
    // TODO: Implement the ViewModel
    private val TAG = javaClass.name
    var response = MutableLiveData<ResponseAdSenseAPI?>(null)
    var responseSitesAPI = MutableLiveData<PerformanceTest?>(null)

    suspend fun getAccount(accessToken: String, accept: String) = withContext(Dispatchers.IO) {
        try {
            val accountResp = RetrofitClient.adSenseService.getAccounts(accessToken, accept).body()
            response.postValue(accountResp)

            Log.d(TAG, "getAccounts: RESPONSE RETROFIT= ${response.value}..")

            // Most probably, it will always be null because we're using
            // it's value on background thread
            response.value?.let { res ->
                Log.d(TAG, "getAccount: DATA FROM CLASS ${res.accounts[0]}")
                Log.d(TAG, "getAccount: DATA : account_name = ${res.accounts[0].name}")
            }
            return@withContext accountResp
        } catch (e: Exception) {
            e.printStackTrace()
            updateUI()
            null
        }
    }

    suspend fun getSites(
        accountId: String,
        accessToken: String,
        accept: String,
        dateRange: String,
        metrics: List<String>,
        dimensions: List<String>
    ) = withContext(Dispatchers.IO) {
        try {
            val accountResp = RetrofitClient.adSenseService.getSitesDetails(
                accountId,
                accessToken,
                accept,
                dateRange,
                metrics,
                dimensions
            ).body()
            responseSitesAPI.postValue(accountResp)

            Log.d(
                TAG,
                "getSites: RESPONSE SITES RETROFIT= ${response.value}.. accountID : ${accountId} and accountResp : ${accountResp}"
            )

            // Most probably, it will always be null because we're using
            // it's value on background thread
            responseSitesAPI.value?.let { res ->
                Log.d(TAG, "getSites: DATA FROM CLASS ${res.rows}")
                Log.d(TAG, "getSites: DATA : Totals = ${res.totals}")
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
