package com.example.adsensedashboard.models.api

data class ResponsePerformanceAPI(
    val averages: Averages,
    val endDate: EndDate,
    val headers: List<Header>,
    val rows: List<Row>,
    val startDate: StartDate,
    val totalMatchedRows: String,
    val totals: Totals
)

data class Row(
    val cells: List<Cell>
)

data class Cell(
    val value: String
)

data class Totals(
    val cells: List<Cell>
)

data class Averages(
    val cells: List<Cell>
)

data class EndDate(
    val day: Int,
    val month: Int,
    val year: Int
)

data class StartDate(
    val day: Int,
    val month: Int,
    val year: Int
)

data class Header(
    val currencyCode: String?,
    val name: String,
    val type: String
)