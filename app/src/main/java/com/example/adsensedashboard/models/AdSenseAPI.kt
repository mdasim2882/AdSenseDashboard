package com.example.adsensedashboard.models

import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AdSenseServiceAPI {
    @GET("v2/accounts/{accountId}/adclients")
    suspend fun getAdClients(@Path("accountId") accountId: String, @Query("key") page: String)

    @GET("v2/accounts")
    suspend fun getAccounts(
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String
    ): JsonElement

}
