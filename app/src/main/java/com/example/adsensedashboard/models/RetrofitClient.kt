package com.example.adsensedashboard.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//SINGLETON CLASS that holds Retrofit Instance

object RetrofitClient {
    private const val BASE_URL = "https://adsense.googleapis.com/"

    val adSenseService: AdSenseServiceAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdSenseServiceAPI::class.java)
    }
}