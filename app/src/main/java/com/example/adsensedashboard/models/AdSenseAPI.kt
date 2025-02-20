package com.example.adsensedashboard.models

import com.example.adsensedashboard.models.api.ResponseAdSenseAPI
import com.example.adsensedashboard.models.api.ResponsePaymentsAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AdSenseServiceAPI {
    @GET("v2/accounts/{accountId}/adclients")
    suspend fun getAdClients(@Path("accountId") accountId: String, @Query("key") page: String)

    @GET("v2/accounts/{accountId}/payments")
    suspend fun getPayments(
        @Path("accountId") accountId: String,
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String
    ): Response<ResponsePaymentsAPI>

    @GET("v2/accounts")
    suspend fun getAccounts(
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String
    ): Response<ResponseAdSenseAPI>

}
