package com.example.adsensedashboard.ui.data

import com.google.gson.Gson

class Temp<T>(private val data: T) {

    fun convertToJson(): String {
        return Gson().toJson(data)
    }
}