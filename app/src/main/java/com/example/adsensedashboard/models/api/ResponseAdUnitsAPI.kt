package com.example.adsensedashboard.models.api

data class ResponseAdUnitsAPI(
    val adUnits: List<AdUnit>
) {
    data class AdUnit(
        val contentAdsSettings: ContentAdsSettings,
        val displayName: String,
        val name: String,
        val reportingDimensionId: String,
        val state: String
    ) {
        data class ContentAdsSettings(
            val size: String,
            val type: String
        )
    }
}