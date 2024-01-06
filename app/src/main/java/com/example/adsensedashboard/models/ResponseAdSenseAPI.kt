package com.example.adsensedashboard.models


data class ResponseAdSenseAPI(
    val accounts: List<Account>
)

data class TimeZone(
    val id: String
)

data class Account(
    val createTime: String,
    val displayName: String,
    val name: String,
    val state: String,
    val timeZone: TimeZone
)

