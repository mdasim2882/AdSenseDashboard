package com.example.adsensedashboard.models.api

data class ResponseAdClientsAPI(
    val adClients: List<AdClient>
) {
    data class AdClient(
        val name: String,
        val productCode: String,
        val reportingDimensionId: String,
        val state: String
    )
}