package com.example.adsensedashboard.models

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//SINGLETON CLASS that holds Retrofit Instance

object RetrofitClient {
    private const val BASE_URL = "https://adsense.googleapis.com/"

    val adSenseService: AdSenseServiceAPI by lazy {
        val client = OkHttpClient.Builder().build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdSenseServiceAPI::class.java)
    }
}