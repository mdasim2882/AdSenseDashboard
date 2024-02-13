package com.example.adsensedashboard.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.adsensedashboard.utils.toast


class PageViewModel(app: Application) : AndroidViewModel(app) {
    private val TAG = "PageViewModel"
    private val MAX_LIST_PAGE_SIZE: Int = 10
    private val appContext = app
    private val _index = MutableLiveData<Int>()
    val response = MutableLiveData("DATA")


    val text: LiveData<String> = _index.map {
        appContext.toast("index: $it")
        when (it) {
            1 -> appContext.toast("on step $it")
            2 -> appContext.toast("on step $it")
        }
//        else -> {
//        appContext.toast("No Data Found...")
//        response.postValue("No Data Found...")
//         }
        "Tab $it"
    }


    fun setIndex(index: Int) {
        Log.d(TAG, "setIndex: VALUE = $index")
        _index.value = index
    }
}