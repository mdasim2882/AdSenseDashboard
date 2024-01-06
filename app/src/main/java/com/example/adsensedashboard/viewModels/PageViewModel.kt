package com.example.adsensedashboard.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.adsensedashboard.ui.data.consoleUI.main.PlaceholderFragment
import com.example.adsensedashboard.utils.api.GetAllAccounts.run
import com.example.adsensedashboard.utils.toast
import com.google.api.services.adsense.v2.model.Account
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PageViewModel(app: Application) : AndroidViewModel(app) {
    private val TAG = "PageViewModel"
    private val MAX_LIST_PAGE_SIZE: Int = 10
    private val appContext = app
    private val _index = MutableLiveData<Int>()
    val response = MutableLiveData("DATA")
    val text: LiveData<String> = _index.map {
        appContext.toast("index: $it")
        when (it) {
            1 -> GlobalScope.launch { callAPIusingClientLibrary(1) }
            2 -> GlobalScope.launch { callAPIusingClientLibrary(2) }
            else -> {
                appContext.toast("No Data Found...")
                response.postValue("No Data Found...")
            }
        }
        ""
    }


    fun callAPIusingClientLibrary(i: @JvmSuppressWildcards Int) {
        Log.d(TAG, "callAPIusingClientLibrary: =============CALLED===========\n")
        val adsense = PlaceholderFragment.initializeAdsense(appContext)
        val accounts: List<Account> = run(adsense!!, MAX_LIST_PAGE_SIZE)
        if ((accounts != null) && !accounts.isEmpty()) {
            Log.d(TAG, "callAPIusingClientLibrary: LIBRARY DATA ${accounts}")
            response.postValue("\n" + "$accounts")
        }
    }

    fun setIndex(index: Int) {
        Log.d(TAG, "setIndex: VALUE = $index")
        _index.value = index
    }
}