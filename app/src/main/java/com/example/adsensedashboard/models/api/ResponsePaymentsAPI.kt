package com.example.adsensedashboard.models.api

data class ResponsePaymentsAPI(
    val payments: List<Payment>
)

data class Payment(
    val amount: String,
    val name: String
)