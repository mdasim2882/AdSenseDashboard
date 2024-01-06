package com.example.adsensedashboard.utils.api

import com.google.api.services.adsense.v2.Adsense
import java.util.Arrays

/*
 * Copyright (c) 2021 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */



/**
 * This example retrieves a report, using a filter for a specified ad client.
 *
 * Tags: accounts.reports.generate
 *
 */
object GenerateReport {
    /**
     * Runs this sample.
     * @param adsense AdSense service object on which to run the requests.
     * @param accountId the ID for the account to be used.
     * @param adClientId the ad client ID on which to run the report.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun run(adsense: Adsense, accountId: String?, adClientId: String) {
        println("=================================================================")
        System.out.printf("Running report for ad client %s\n", adClientId)
        println("=================================================================")
        val request = adsense.accounts().reports().generate(accountId)

        // Specify the startDate and endDate for the report.
        request.setDateRange("CUSTOM")
        request.setStartDateYear(2021).setStartDateMonth(3).setStartDateDay(1)
        request.setEndDateYear(2021).setEndDateMonth(3).setEndDateDay(31)

        // Specify the desired ad client using a filter.
        request.setFilters(Arrays.asList("AD_CLIENT_ID==" + escapeFilterParameter(adClientId)))

        // Specify the desired metrics and dimensions.
        request.setMetrics(
            mutableListOf(
                "PAGE_VIEWS", "AD_REQUESTS", "AD_REQUESTS_COVERAGE", "CLICKS",
                "AD_REQUESTS_CTR", "COST_PER_CLICK", "AD_REQUESTS_RPM", "ESTIMATED_EARNINGS"
            )
        )
        request.setDimensions(mutableListOf("DATE"))

        // Sort by ascending date.
        request.setOrderBy(mutableListOf("+DATE"))

        // Run report.
        val response = request.execute()
        val rows = response.rows
        if (rows != null && !rows.isEmpty()) {
            // Display headers.
            for (header in response.headers) {
                System.out.printf("%25s", header.name)
            }
            println()

            // Display results.
            for (row in rows) {
                for (cell in row.cells) {
                    System.out.printf("%25s", cell.value)
                }
                println()
            }
            println()
        } else {
            println("No rows returned.")
        }
        println()
    }

    /**
     * Escape special characters for a parameter being used in a filter.
     * @param parameter the parameter to be escaped.
     * @return the escaped parameter.
     */
    fun escapeFilterParameter(parameter: String): String {
        // Get the last entry of the parameter name (after the last forward slash) for filtering.
        var parameter = parameter
        parameter = parameter.substring(parameter.lastIndexOf("/") + 1)
        // Replace special characters with the escaped version and return the string.
        return parameter.replace("\\", "\\\\").replace(",", "\\,")
    }
}