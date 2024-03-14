package com.example.adsensedashboard.models

import com.example.adsensedashboard.models.api.PerformanceTest
import com.example.adsensedashboard.models.api.ResponseAdClientsAPI
import com.example.adsensedashboard.models.api.ResponseAdSenseAPI
import com.example.adsensedashboard.models.api.ResponseAdUnitsAPI
import com.example.adsensedashboard.models.api.ResponsePaymentsAPI
import com.example.adsensedashboard.models.api.ResponsePerformanceAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AdSenseServiceAPI {

    @GET("v2/accounts/{accountId}/payments")
    suspend fun getPayments(
        @Path("accountId") accountId: String,
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String
    ): Response<ResponsePaymentsAPI>

    @GET("v2/accounts")
    suspend fun getAccounts(
        @Header("Authorization") authorization: String, @Header("Accept") accept: String
    ): Response<ResponseAdSenseAPI>

    @GET("v2/accounts/{accountId}/reports:generate")
    suspend fun getPerformanceDetails(
        @Path("accountId") accountId: String,
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String,
        @Query("dateRange") dateRange: String,
        @Query("metrics") metrics: List<String>
    ): Response<ResponsePerformanceAPI>

    @GET("v2/accounts/{accountId}/reports:generate")
    suspend fun getSitesDetails(
        @Path("accountId") accountId: String,
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String,
        @Query("dateRange") dateRange: String,
        @Query("metrics") metrics: List<String>,
        @Query("dimensions") dimensions: List<String>
    ): Response<PerformanceTest>

    @GET("v2/accounts/{accountId}/adclients")
    suspend fun getAdClientsDetails(
        @Path("accountId") accountId: String,
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String,
    ): Response<ResponseAdClientsAPI>

    @GET("v2/accounts/{accountId}/adclients/{adClientsId}/adunits")
    suspend fun getAdUnitsDetails(
        @Path("accountId") accountId: String,
        @Path("adClientsId") adClientsId: String,
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String,
    ): Response<ResponseAdUnitsAPI>


}
