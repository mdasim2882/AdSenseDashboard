package com.example.adsensedashboard.models

import retrofit2.http.GET
import retrofit2.http.Path

interface AdSenseServiceAPI {
    @GET("v2/accounts/{accountId}/adclients")
    suspend fun getAdClients(@Path("accountId") accountId: String)
}
