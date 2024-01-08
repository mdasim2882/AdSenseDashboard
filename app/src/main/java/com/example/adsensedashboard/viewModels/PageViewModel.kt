package com.example.adsensedashboard.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.adsensedashboard.ui.data.consoleUI.main.PlaceholderFragment
import com.example.adsensedashboard.utils.api.GenerateReport
import com.example.adsensedashboard.utils.api.GenerateSavedReport
import com.example.adsensedashboard.utils.api.GetAccountTree
import com.example.adsensedashboard.utils.api.GetAllAccounts.run
import com.example.adsensedashboard.utils.api.GetAllAdClients
import com.example.adsensedashboard.utils.api.GetAllAdUnits
import com.example.adsensedashboard.utils.api.GetAllAdUnitsForCustomChannel
import com.example.adsensedashboard.utils.api.GetAllAlerts
import com.example.adsensedashboard.utils.api.GetAllCustomChannels
import com.example.adsensedashboard.utils.api.GetAllCustomChannelsForAdUnit
import com.example.adsensedashboard.utils.api.GetAllSavedReports
import com.example.adsensedashboard.utils.api.GetAllUrlChannels
import com.example.adsensedashboard.utils.toast
import com.google.api.services.adsense.v2.model.AdClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PageViewModel(app: Application) : AndroidViewModel(app) {
    private val TAG = "PageViewModel"
    private val MAX_LIST_PAGE_SIZE: Int = 10
    private val appContext = app
    private val _index = MutableLiveData<Int>()
    val response = MutableLiveData("DATA")

    @OptIn(DelicateCoroutinesApi::class)
    val text: LiveData<String> = _index.map {
        appContext.toast("index: $it")
        when (it) {
            1 -> GlobalScope.launch { loadDataFromAPI(1) }
            2 -> GlobalScope.launch { loadDataFromAPI(2) }
            else -> {
                appContext.toast("No Data Found...")
                response.postValue("No Data Found...")
            }
        }
        ""
    }

    private fun loadDataFromAPI(i: Int) {
        Log.d(TAG, "loadDataFromAPI() called with: i = $i")
        try {
            val adsense = PlaceholderFragment.initializeAdsense(appContext)
            adsense?.also { adsense ->
                val accounts = run(adsense, MAX_LIST_PAGE_SIZE)
                if (accounts != null && !accounts.isEmpty()) {
                    // Get an example account ID, so we can run the following sample.
                    if (i == 1) {
                        Log.d(TAG, "loadDataFromAPI: LIBRARY DATA ${accounts}")
                        response.postValue("\n" + "$accounts")
                        return
                    }

                    val chosenAccount: String = accounts[0].name;
                    GetAccountTree.run(adsense, chosenAccount)
                    val adClients: List<AdClient> =
                        GetAllAdClients.run(adsense, chosenAccount, MAX_LIST_PAGE_SIZE)
                    if (adClients != null && !adClients.isEmpty()) {
                        // Get an ad client ID, so we can run the rest of the samples.
                        val exampleAdClientId: String = adClients[0].getName()
                        val units =
                            GetAllAdUnits.run(adsense, exampleAdClientId, MAX_LIST_PAGE_SIZE)
                        if (units != null && !units.isEmpty()) {
                            // Get an example ad unit ID, so we can run the following sample.
                            val exampleAdUnitId = units[0].name
                            GetAllCustomChannelsForAdUnit.run(
                                adsense,
                                exampleAdUnitId,
                                MAX_LIST_PAGE_SIZE
                            )
                        }
                        val channels = GetAllCustomChannels.run(
                            adsense,
                            exampleAdClientId, MAX_LIST_PAGE_SIZE
                        )
                        if (channels != null && !channels.isEmpty()) {
                            // Get an example custom channel ID, so we can run the following sample.
                            val exampleCustomChannelId = channels[1].name
                            GetAllAdUnitsForCustomChannel.run(
                                adsense,
                                exampleCustomChannelId,
                                MAX_LIST_PAGE_SIZE
                            )
                        }
                        GetAllUrlChannels.run(adsense, exampleAdClientId, MAX_LIST_PAGE_SIZE)
                        val report = GenerateReport.run(adsense, chosenAccount, exampleAdClientId)
                        if (i == 2) {
                            response.postValue(report)
                            return
                        }
                    } else {
                        println("No ad clients found, unable to run remaining methods.")
                    }
                    val savedReports =
                        GetAllSavedReports.run(adsense, chosenAccount, MAX_LIST_PAGE_SIZE)
                    if (savedReports != null && !savedReports.isEmpty()) {
                        // Get a saved report ID, so we can generate its report.
                        val exampleSavedReportId = savedReports[0].name
                        GenerateSavedReport.run(adsense, exampleSavedReportId)
                    } else {
                        println("No saved report found.")
                    }
                    GetAllAlerts.run(adsense, chosenAccount)
                }
            }
        } catch (e: Exception) {
            // TODO: "Handle Client Libraries exception here"
            println("ERROR Message : ${e.message}")
        }
    }


    fun setIndex(index: Int) {
        Log.d(TAG, "setIndex: VALUE = $index")
        _index.value = index
    }
}