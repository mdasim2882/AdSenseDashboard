package com.example.adsensedashboard.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.example.adsensedashboard.models.RetrofitClient
import com.example.adsensedashboard.models.api.ResponseAdSenseAPI
import com.example.adsensedashboard.models.api.ResponsePaymentsAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DashboardViewModel(
    app: Application
) : AndroidViewModel(app), DefaultLifecycleObserver {

    private val TAG = "DashboardViewModel"
    var response = MutableLiveData<ResponseAdSenseAPI?>(null)
    var responsePaymentsAPI = MutableLiveData<ResponsePaymentsAPI?>(null)

    suspend fun getAccount(accessToken: String, accept: String) =
        withContext(Dispatchers.IO) {
            try {
                val accountResp = RetrofitClient.adSenseService
                    .getAccounts(accessToken, accept).body()
                response.postValue(accountResp)

                Log.d(TAG, "getAccounts: RESPONSE RETROFIT= ${response.value}..")
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

    suspend fun getPayments(accountId: String, accessToken: String, accept: String) =
        withContext(Dispatchers.IO) {
            try {
                val accountResp = RetrofitClient.adSenseService
                    .getPayments(accountId, accessToken, accept).body()
                responsePaymentsAPI.postValue(accountResp)

                Log.d(TAG, "getPayments: RESPONSE PAYMENT RETROFIT= ${responsePaymentsAPI.value}..")
                // TODO: Need to fix this call as it is getting called immediately with null value
                //  while the response is loading... hence not providing payment response after
                //  fetching account data

                responsePaymentsAPI.value?.let { res ->
                    Log.d(TAG, "getPayments: DATA FROM CLASS ${res.payments}")
                    Log.d(TAG, "getPayments: DATA : ammount = ${res.payments[0].amount}")
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